package bloodcenter.controller;

import bloodcenter.enums.BloodType;
import bloodcenter.model.Blood;
import bloodcenter.service.BloodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/blood")
public class BloodController {

    private final BloodService bloodService;

    @Autowired
    public BloodController(BloodService bloodService) {
        this.bloodService = bloodService;
    }

    @GetMapping
    public List<Blood> getBlood() {
        return bloodService.getBlood();
    }

    @GetMapping("/type")
    public boolean getBloodType(@RequestParam BloodType bloodType) {
        return bloodService.getBloodType(bloodType);
    }

    @GetMapping("/type/quantity")
    public boolean getBloodType(@RequestParam BloodType bloodType, @RequestParam Float quantity) {
        return bloodService.getBloodTypeQuantity(bloodType, quantity);
    }
}
