package bloodcenter.available_appointment;

import bloodcenter.appointment.AppointmentService;
import bloodcenter.available_appointment.dto.AvailableAppointmentsDTO;
import bloodcenter.branch_center.dto.RegisterBranchCenterDTO;
import bloodcenter.person.model.BCAdmin;
import bloodcenter.utils.ObjectsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("api/available-appointment")
public class AvailableAppointmentController {
    private final AvailableAppointmentService availabeAppointmentService;

    @Autowired
    public AvailableAppointmentController(AvailableAppointmentService service){

        this.availabeAppointmentService = service;
    }

    @GetMapping()
    @Secured({"ROLE_BCADMIN"})
    public List<AvailableAppointmentsDTO> getAll(HttpServletRequest request) throws BCAdmin.BCAdminNotFoundException {
        return availabeAppointmentService.getAll(request);
    }

    @PostMapping
    @Secured({"ROLE_BCADMIN"})
    public ResponseEntity<Object> createAvailableAppointment(HttpServletRequest request,@RequestBody AvailableAppointmentsDTO appointmentsDTO) throws BCAdmin.BCAdminNotFoundException {
        availabeAppointmentService.create(request,appointmentsDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
