package bloodcenter.appointment;

import bloodcenter.appointment.dto.CreateAppointmentDTO;
import bloodcenter.branch_center.dto.RegisterBranchCenterDTO;
import bloodcenter.utils.ObjectsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<Object> GetById(@PathVariable("id") long id){
        return new ResponseEntity<>(ObjectsMapper.convertAppointmentToDTO(service.GetById(id)), HttpStatus.OK);
    }

    @GetMapping("/is-capable-for-blood-donation/{userId}")
    @Secured({"ROLE_USER"})
    public ResponseEntity<Object> IsCapableForBloodDonation(@PathVariable("userId") long userId){
        return new ResponseEntity<>(service.HaveYouGiveBloodLastSixMonths(userId), HttpStatus.OK);
    }

    @PostMapping("/user-schedule")
    @Secured({"ROLE_USER"})
    public ResponseEntity<Object> createNewAppointment(@RequestBody CreateAppointmentDTO appointmentDTO) {
        service.userCreateAppointment(appointmentDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
