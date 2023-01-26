package bloodcenter.blood;

import bloodcenter.api_key.Key;
import bloodcenter.api_key.KeyService;
import bloodcenter.core.ErrorResponse;
import bloodcenter.location_simulator.LocationSimulatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/blood")
public class BloodController {
    private final BloodService bloodService;
    private final KeyService keyService;
    private final LocationSimulatorService simulatorService;
    @Autowired
    public BloodController(BloodService bloodService, KeyService keyService, LocationSimulatorService simulatorService){
        this.bloodService = bloodService;
        this.keyService = keyService;
        this.simulatorService = simulatorService;
    }

    @GetMapping
    public List<Blood> getBlood() {
        return bloodService.getBlood();
    }

    @GetMapping("/type")
    public ResponseEntity<Boolean> getBloodType(@RequestParam String email,
                                                @RequestParam String bloodType,
                                                @RequestHeader("Authorization") String auth) throws Exception {
        if(!keyService.isKeyValid(email, auth)) throw new Key.InvalidKeyException("Invalid api key");
        return new ResponseEntity<>(bloodService.getBloodType(bloodType), HttpStatus.OK);
    }

    @GetMapping("/type/quantity")
    public ResponseEntity<Boolean> getBloodType(@RequestParam String email,
                                                @RequestParam String bloodType,
                                                @RequestParam Float quantity,
                                                @RequestHeader("Authorization") String auth) throws Exception {
        if(!keyService.isKeyValid(email, auth)) throw new Key.InvalidKeyException("Invalid api key");
        return new ResponseEntity<>(bloodService.checkBloodAvailabilityWithQuantity(bloodType, quantity), HttpStatus.OK);
    }

    @PatchMapping("/tender/confirm")
    public ResponseEntity<Boolean> confirmTender(){
        double A_lng = 19.849729;
        double A_lat = 45.245186;
        double B_lng = 19.824388;
        double B_lat = 45.259405;
        simulatorService.initiateLocationService(A_lng, A_lat, B_lng, B_lat);
        return new ResponseEntity<>(true, HttpStatus.OK);
    }

    @ExceptionHandler({ Key.InvalidKeyException.class })
    public ResponseEntity<Object> handleKeyExceptions(Exception ex){
        ex.printStackTrace();
        return new ResponseEntity<>(new ErrorResponse(ex.getMessage()), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler({ Exception.class })
    public ResponseEntity<Object> handleExceptions(Exception ex){
        ex.printStackTrace();
        return new ResponseEntity<>(new ErrorResponse(ex.getMessage()), HttpStatus.BAD_REQUEST);
    }
}
