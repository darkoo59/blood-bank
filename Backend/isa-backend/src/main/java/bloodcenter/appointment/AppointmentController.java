package bloodcenter.appointment;

import bloodcenter.utils.ObjectsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
