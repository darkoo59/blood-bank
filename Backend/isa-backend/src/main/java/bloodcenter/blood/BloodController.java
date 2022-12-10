package bloodcenter.blood;

import bloodcenter.api_key.Key;
import bloodcenter.api_key.KeyService;
import bloodcenter.core.ErrorResponse;
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
    @Autowired
    public BloodController(BloodService bloodService, KeyService keyService){
        this.bloodService = bloodService;
        this.keyService = keyService;
    }

    @GetMapping
    public List<Blood> getBlood() {
        return bloodService.getBlood();
    }

    @GetMapping("/type")
    public ResponseEntity<Boolean> getBloodType(@RequestParam String email,
                                                @RequestParam BloodType bloodType,
                                                @RequestHeader("Authorization") String auth) throws Exception {
        if(!keyService.isKeyValid(email, auth)) throw new Key.InvalidKeyException("Invalid api key");
        return new ResponseEntity<>(bloodService.getBloodType(bloodType), HttpStatus.OK);
    }

    @GetMapping("/type/quantity")
    public ResponseEntity<Boolean> getBloodType(@RequestParam String email,
                                                @RequestParam BloodType bloodType,
                                                @RequestParam Float quantity,
                                                @RequestHeader("Authorization") String auth) throws Exception {
        if(!keyService.isKeyValid(email, auth)) throw new Key.InvalidKeyException("Invalid api key");
        return new ResponseEntity<>(bloodService.getBloodTypeQuantity(bloodType, quantity), HttpStatus.OK);
    }

    @PatchMapping("/tender/confirm")
    public ResponseEntity<Boolean> confirmTender(){
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
