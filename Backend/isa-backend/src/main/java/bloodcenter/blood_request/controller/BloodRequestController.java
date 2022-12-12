package bloodcenter.blood_request.controller;

import bloodcenter.blood_request.dto.ReceiveBloodRequestDTO;
import bloodcenter.blood_request.service.BloodRequestService;
import bloodcenter.subscribed_hospitals.dto.HospitalDTO;
import bloodcenter.subscribed_hospitals.service.SubscribedHospitalService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/blood-request")
public class BloodRequestController {
    private final BloodRequestService bloodRequestService;

    public BloodRequestController(BloodRequestService service){
        this.bloodRequestService = service;
    }
    @PostMapping()
    public void sendBlood(@RequestBody ReceiveBloodRequestDTO bloodDTO){
        this.bloodRequestService.sendBlood(bloodDTO);
    }
}
