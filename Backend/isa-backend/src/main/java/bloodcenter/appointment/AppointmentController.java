package bloodcenter.appointment;

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

    @PatchMapping("/start/{id}")
    @Secured({"ROLE_BCADMIN"})
    public ResponseEntity<Object> StartAppointment(@PathVariable("id") int id){


        return new ResponseEntity<>(HttpStatus.OK);
    }
}
