package bloodcenter.api_key;

import bloodcenter.core.ErrorResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Random;

@RestController
@RequestMapping("api/key")
public class KeyController {
    private final KeyService keyService;
    @Autowired
    public KeyController(KeyService keyService){
        this.keyService = keyService;
    }

    @PostMapping("/create")
    public String createKey(@RequestBody String email) throws Key.ApiKeyAlreadyExistsException {
        return keyService.createKey(email);
    }

    @ExceptionHandler({ Key.ApiKeyAlreadyExistsException.class })
    public ResponseEntity<Object> handleKeyExceptions(Exception ex){
        ex.printStackTrace();
        return new ResponseEntity<>(new ErrorResponse(ex.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({ Exception.class })
    public ResponseEntity<Object> handleExceptions(Exception ex){
        ex.printStackTrace();
        return new ResponseEntity<>(new ErrorResponse(ex.getMessage()), HttpStatus.UNAUTHORIZED);
    }
}
