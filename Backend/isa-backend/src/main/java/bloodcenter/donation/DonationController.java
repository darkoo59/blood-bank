package bloodcenter.donation;

import bloodcenter.core.ErrorResponse;
import bloodcenter.donation.dto.CreateDonationDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/donation")
public class DonationController {
    private final DonationService service;

    public DonationController(DonationService service){
        this.service = service;
    }

    @PostMapping
    @Secured({"ROLE_BCADMIN"})
    public ResponseEntity<Object> StartAppointment(@RequestBody CreateDonationDTO dto) throws Exception {
        service.createDonation(dto);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ExceptionHandler({ Exception.class })
    public ResponseEntity<Object> handleExceptions(Exception ex){
        ex.printStackTrace();
        return new ResponseEntity<>(new ErrorResponse(ex.getMessage()), HttpStatus.BAD_REQUEST);
    }
}
