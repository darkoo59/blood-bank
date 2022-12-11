package bloodcenter.subscribed_hospitals.controller;

import bloodcenter.MQConfig;
import bloodcenter.feedback.FeedbackService;
import bloodcenter.news.model.NewsContent;
import bloodcenter.subscribed_hospitals.dto.HopsitalToUnsubscribeDTO;
import bloodcenter.subscribed_hospitals.dto.HospitalDTO;
import bloodcenter.subscribed_hospitals.model.SubscribedHospital;
import bloodcenter.subscribed_hospitals.service.SubscribedHospitalService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/subscribed-hospital")
public class SubscribedHospitalController {
    private final SubscribedHospitalService subscribedHospitalService;

    public SubscribedHospitalController(SubscribedHospitalService service){
        this.subscribedHospitalService = service;
    }

    @PostMapping("/subscribe")
    public void publishMessage(@RequestBody HospitalDTO hospitalDTO){
        this.subscribedHospitalService.subscribe(hospitalDTO);
    }

    @PostMapping("/unsubscribe")
    public void unsubscribe(@RequestBody HopsitalToUnsubscribeDTO hospitalToUnsubscribe) throws SubscribedHospital.HospitalNotFoundException {
        System.out.println(hospitalToUnsubscribe.getEmail());
        this.subscribedHospitalService.unsubscribe(hospitalToUnsubscribe.getEmail());
    }

}
