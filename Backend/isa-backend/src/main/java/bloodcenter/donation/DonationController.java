package bloodcenter.donation;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/donation")
public class DonationController {
    @PostMapping
    @Secured({"ROLE_BCADMIN"})
    public ResponseEntity<Object> StartAppointment(){


        return new ResponseEntity<>(HttpStatus.OK);
    }
}
