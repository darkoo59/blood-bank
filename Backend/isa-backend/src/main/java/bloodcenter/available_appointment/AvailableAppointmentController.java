package bloodcenter.available_appointment;

import bloodcenter.appointment.AppointmentService;
import bloodcenter.available_appointment.dto.AvailableAppointmentsDTO;
import bloodcenter.core.ErrorResponse;
import bloodcenter.exceptions.QuestionnaireNotCompleted;
import bloodcenter.exceptions.UserCannotGiveBloodException;
import bloodcenter.person.model.BCAdmin;
import bloodcenter.security.filter.AuthUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("api/available-appointment")
public class AvailableAppointmentController {
    private final AvailableAppointmentService availabeAppointmentService;
    private final AppointmentService appointmentService;

    @Autowired
    public AvailableAppointmentController(AvailableAppointmentService service, AppointmentService appointmentService){
        this.availabeAppointmentService = service;
        this.appointmentService = appointmentService;
    }

    @GetMapping()
    @Secured({"ROLE_BCADMIN"})
    public List<AvailableAppointmentsDTO> getAll(HttpServletRequest request) throws BCAdmin.BCAdminNotFoundException {
        return availabeAppointmentService.getAll(request);
    }

    @PostMapping
    @Secured({"ROLE_BCADMIN"})
    public ResponseEntity<Object> createAvailableAppointment(HttpServletRequest request,@RequestBody AvailableAppointmentsDTO appointmentsDTO) throws Exception {
        availabeAppointmentService.create(request,appointmentsDTO);
        return new ResponseEntity<>(OK);
    }

    @PutMapping("/schedule")
    @Secured({"ROLE_USER"})
    public ResponseEntity<?> scheduleAppointment(HttpServletRequest request, @RequestBody Long id) {
        try {
            String userEmail = AuthUtility.getEmailFromRequest(request);
            appointmentService.scheduleAppointment(userEmail, id);
            return new ResponseEntity<>(OK);
        } catch (UserCannotGiveBloodException | QuestionnaireNotCompleted e) {
            return new ResponseEntity<>(e.getMessage(), BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("Unknown error occurred", BAD_REQUEST);
        }
    }

    @ExceptionHandler({ Exception.class })
    public ResponseEntity<Object> handleExceptions(Exception ex){
        ex.printStackTrace();
        return new ResponseEntity<>(new ErrorResponse(ex.getMessage()), HttpStatus.BAD_REQUEST);
    }
}
