package bloodcenter.appointment;

import bloodcenter.appointment.dto.CreateAppointmentDTO;
import bloodcenter.core.ErrorResponse;
import bloodcenter.exceptions.AppointmentDoesNotExistException;
import bloodcenter.exceptions.CancellationTooLateException;
import bloodcenter.utils.ObjectsMapper;
import com.google.zxing.WriterException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.io.IOException;

@RestController
@RequestMapping("api/appointment")
public class AppointmentController {
    private final AppointmentService service;

    @Autowired
    public AppointmentController(AppointmentService service){
        this.service = service;
    }

    @GetMapping("/{id}")
    @Secured({"ROLE_USER", "ROLE_BCADMIN"})
    public ResponseEntity<Object> getById(@PathVariable("id") long id) throws Exception {
        return new ResponseEntity<>(
                ObjectsMapper.convertAppointmentToDTO(service.getById(id)),
                HttpStatus.OK);
    }

    @PatchMapping("/start/{id}")
    @Secured({"ROLE_BCADMIN"})
    public ResponseEntity<Object> startAppointment(@PathVariable("id") long id){
        service.startAppointment(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping()
    @Secured({"ROLE_USER", "ROLE_BCADMIN"})
    public ResponseEntity<Object> getAllAppointments(){
        return new ResponseEntity<>(ObjectsMapper.convertAppointmentListToDTO(service.getAll()), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @Secured({"ROLE_BCADMIN"})
    public ResponseEntity<Object> deleteAppointment(@PathVariable("id") long id){
        service.deleteAppointment(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/is-capable-for-blood-donation/{userId}")
    @Secured({"ROLE_USER"})
    public ResponseEntity<Object> IsCapableForBloodDonation(@PathVariable("userId") long userId){
        return new ResponseEntity<>(service.hasDonatedBloodInLastSixMonths(userId), HttpStatus.OK);
    }

    @PostMapping("/user-schedule")
    @Secured({"ROLE_USER"})
    public ResponseEntity<Object> createNewAppointment(@RequestBody CreateAppointmentDTO appointmentDTO) throws MessagingException, IOException, WriterException {
        service.userCreateAppointment(appointmentDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    
    @GetMapping("/all-for-user/{userId}")
    @Secured({"ROLE_USER"})
    public ResponseEntity<Object> getAllAppointmentsByUserId(@PathVariable("userId") long userId){
        return new ResponseEntity<>(service.findAllInFutureByUserId(userId), HttpStatus.OK);
    }

    @PatchMapping("/cancel/{appointmentId}")
    @Secured({"ROLE_USER"})
    public ResponseEntity<?> cancelAppointment(@PathVariable("appointmentId") long id) throws AppointmentDoesNotExistException, CancellationTooLateException {
        service.cancelAppointment(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ExceptionHandler({ Exception.class })
    public ResponseEntity<Object> handleExceptions(Exception ex) {
        ex.printStackTrace();
        return new ResponseEntity<>(new ErrorResponse(ex.getMessage()), HttpStatus.BAD_REQUEST);
    }
}
