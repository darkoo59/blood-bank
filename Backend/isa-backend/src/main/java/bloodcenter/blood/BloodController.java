package bloodcenter.blood;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/blood")
public class BloodController {

    @Autowired
    private BloodService bloodService;

    @GetMapping
    public List<Blood> getBlood() {
        return bloodService.getBlood();
    }

    @GetMapping("/type")
    public ResponseEntity<Boolean> getBloodType(@RequestParam BloodType bloodType,
                                                @RequestHeader("Authorization") String auth) {
        String[] arr = auth.split(" ");
        if(arr.length != 2 || !arr[1].equals("6c66af456eaf")){
            return new ResponseEntity<>(false, HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>(bloodService.getBloodType(bloodType), HttpStatus.OK);
    }

    @GetMapping("/type/quantity")
    public ResponseEntity<Boolean> getBloodType(@RequestParam BloodType bloodType,
                                                @RequestParam Float quantity,
                                                @RequestHeader("Authorization") String auth) {
        String[] arr = auth.split(" ");
        if(arr.length != 2 || !arr[1].equals("6c66af456eaf")){
            return new ResponseEntity<>(false, HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>(bloodService.getBloodTypeQuantity(bloodType, quantity), HttpStatus.OK);
    }
}
