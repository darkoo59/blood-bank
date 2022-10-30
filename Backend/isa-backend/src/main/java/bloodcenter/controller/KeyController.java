package bloodcenter.controller;

import bloodcenter.service.KeyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/key")
public class KeyController {

    @Autowired
    private KeyService keyService;

    @PostMapping("/create")
    public String createKey(@RequestBody String email) {
        return "6c66af456eaf";
    }
}
