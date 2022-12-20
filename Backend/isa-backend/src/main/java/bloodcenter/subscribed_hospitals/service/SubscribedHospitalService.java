package bloodcenter.subscribed_hospitals.service;

import bloodcenter.MQConfig;
import bloodcenter.MQConfigBloodSupply;
import bloodcenter.MQConfigNotification;
import bloodcenter.address.AddressService;
import bloodcenter.blood.Blood;
import bloodcenter.blood.BloodService;
import bloodcenter.branch_center.BranchCenter;
import bloodcenter.branch_center.BranchCenterRepository;
import bloodcenter.feedback.FeedbackRepository;
import bloodcenter.subscribed_hospitals.dto.BloodToSendDTO;
import bloodcenter.subscribed_hospitals.dto.HospitalDTO;
import bloodcenter.subscribed_hospitals.dto.NoBloodNotificationDTO;
import bloodcenter.subscribed_hospitals.model.SubscribedHospital;
import bloodcenter.subscribed_hospitals.repository.SubscribedHospitalRepository;
import bloodcenter.utils.ObjectsMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

@Service
public class SubscribedHospitalService {
    private final SubscribedHospitalRepository subscribedHospitalRepository;
    private final BloodService bloodService;
    private final RestTemplate restTemplate;
    @Autowired
    private RabbitTemplate bloodTemplate;

    public SubscribedHospitalService(SubscribedHospitalRepository repository, BloodService bloodService) {
        this.subscribedHospitalRepository = repository;
        this.bloodService = bloodService;
        this.restTemplate = new RestTemplate();
    }

    public void subscribe(HospitalDTO hospitalDTO){
        SubscribedHospital hospitalToSubscribe = ObjectsMapper.convertHospitalDTOToSubscribedHospital(hospitalDTO);
        subscribedHospitalRepository.save(hospitalToSubscribe);
    }

    public void unsubscribe(String email) throws SubscribedHospital.HospitalNotFoundException {
        System.out.println(email);
        List<SubscribedHospital> allHospitals = subscribedHospitalRepository.findAll();
        Optional<SubscribedHospital> hospitalToUnsubscribe = subscribedHospitalRepository.findSubscribedHospitalByEmail(email);
        if(hospitalToUnsubscribe.isEmpty()){
            throw new SubscribedHospital.HospitalNotFoundException("Hospital not found.");
        }
            subscribedHospitalRepository.delete(hospitalToUnsubscribe.get());
    }

    // monthly                  every month
    @Scheduled(cron="0 * * * * *")
    public void sendBloodSupplyToHospital() throws URISyntaxException {
        for(SubscribedHospital hospital:subscribedHospitalRepository.findAll()){
            if(bloodService.getBloodTypeQuantity(hospital.getBloodType(),hospital.getQuantity())) { // salji krv na psw(rabbitmq)
                BloodToSendDTO bloodToSend = new BloodToSendDTO();
                bloodToSend.setBloodType(hospital.getBloodType().toString());
                bloodToSend.setQuantity(hospital.getQuantity());
                List<Blood> allBlood = bloodService.getBlood();
                for (Blood blood:allBlood) {
                   if(blood.getType() == hospital.getBloodType()){
                       blood.setQuantity(blood.getQuantity() - bloodToSend.getQuantity());
                       break;
                }
            }
                bloodService.saveBlood(allBlood);
                bloodTemplate.convertAndSend(MQConfigBloodSupply.EXCHANGE, MQConfigBloodSupply.ROUTING_KEY, bloodToSend);
//                URI uri = new URI("http://localhost:45488/api/BloodSubscription/receive-blood");
//                ResponseEntity<Void> result = restTemplate.postForEntity(uri, bloodToSend, Void.class);
            }else{
                NoBloodNotificationDTO notificationToSend = new NoBloodNotificationDTO();
                notificationToSend.setTitle("There is no enough blood.");
                notificationToSend.setContent("Blood bank "+hospital.getHospitalName()+" don't have enough blood for " +
                        "monthly supply right now. Please contact us for more information.");
                bloodTemplate.convertAndSend(MQConfigNotification.EXCHANGE, MQConfigNotification.ROUTING_KEY, notificationToSend);
//                URI uri = new URI("http://localhost:45488/api/BloodSubscription/receive-notification");
//                ResponseEntity<Void> result = restTemplate.postForEntity(uri, notificationToSend, Void.class);
            }
        }
    }
}
