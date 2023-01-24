package bloodcenter.blood_request.service;

import bloodcenter.MQConfigBloodSupply;
import bloodcenter.MQConfigNotification;
import bloodcenter.blood.Blood;
import bloodcenter.blood.BloodService;
import bloodcenter.blood.BloodType;
import bloodcenter.blood_request.dto.ReceiveBloodRequestDTO;
import bloodcenter.blood_request.model.BloodRequest;
import bloodcenter.blood_request.repository.BloodRequestRepository;
import bloodcenter.subscribed_hospitals.dto.BloodToSendDTO;
import bloodcenter.subscribed_hospitals.dto.NoBloodNotificationDTO;
import bloodcenter.subscribed_hospitals.model.SubscribedHospital;
import bloodcenter.subscribed_hospitals.repository.SubscribedHospitalRepository;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class BloodRequestService {
    @Autowired
    private RabbitTemplate bloodTemplate;
    private final BloodService bloodService;
    private final BloodRequestRepository bloodRequestRepository;

    public BloodRequestService(BloodService bloodService, BloodRequestRepository repository) {
        this.bloodService = bloodService;
        this.bloodRequestRepository = repository;
    }
    public void sendBlood(ReceiveBloodRequestDTO dto){
        BloodType bloodType = getBloodType(dto.getBloodType());
        if(dto.isUrgent()){
            if(bloodService.getBloodTypeQuantity(bloodType,dto.getQuantityInLiters())) { // salji krv na psw(rabbitmq)
                BloodToSendDTO bloodToSend = new BloodToSendDTO();
                bloodToSend.setBloodType(bloodType.toString());
                bloodToSend.setQuantity(dto.getQuantityInLiters());
                List<Blood> allBlood = bloodService.getBlood();
                for (Blood blood:allBlood) {
                    if(blood.getType() == bloodType){
                        blood.setQuantity(blood.getQuantity() - bloodToSend.getQuantity());
                        break;
                    }
                }
                bloodService.saveBlood(allBlood);
                bloodTemplate.convertAndSend(MQConfigBloodSupply.EXCHANGE, MQConfigBloodSupply.ROUTING_KEY, bloodToSend);
            }else{
                NoBloodNotificationDTO notificationToSend = new NoBloodNotificationDTO();
                notificationToSend.setTitle("There is no enough blood for urgent supply.");
                notificationToSend.setContent("Blood bank don't have enough blood for " +
                        "urgent supply right now. Please contact us for more information. Or try again later.");
                bloodTemplate.convertAndSend(MQConfigNotification.EXCHANGE, MQConfigNotification.ROUTING_KEY, notificationToSend);
            }
        }else{
            BloodRequest requestToCreate = new BloodRequest();
            requestToCreate.setBloodType(getBloodType(dto.getBloodType()));
            requestToCreate.setQuantity(dto.getQuantityInLiters());
            requestToCreate.setFinalDate(dto.getFinalDate());
            bloodRequestRepository.save(requestToCreate);

        }
    }

    @Scheduled(cron="0 * * * * *")
    public void sendBloodSupplyToHospital() throws URISyntaxException {
        for(BloodRequest request:bloodRequestRepository.findAll()){
            if(request.getFinalDate().isBefore(LocalDateTime.now())){
                if(bloodService.getBloodTypeQuantity(request.getBloodType(), request.getQuantity())) { // salji krv na psw(rabbitmq)
                    BloodToSendDTO bloodToSend = new BloodToSendDTO();
                    bloodToSend.setBloodType(request.getBloodType().toString());
                    bloodToSend.setQuantity(request.getQuantity());
                    List<Blood> allBlood = bloodService.getBlood();
                    for (Blood blood:allBlood) {
                        if(blood.getType() == request.getBloodType()){
                            blood.setQuantity(blood.getQuantity() - bloodToSend.getQuantity());
                            break;
                        }
                    }
                    bloodService.saveBlood(allBlood);
                    bloodRequestRepository.delete(request);
                    bloodTemplate.convertAndSend(MQConfigBloodSupply.EXCHANGE, MQConfigBloodSupply.ROUTING_KEY, bloodToSend);
                }else{
                    NoBloodNotificationDTO notificationToSend = new NoBloodNotificationDTO();
                    notificationToSend.setTitle("There is no enough blood for scheduled supply at: " + request.getFinalDate());
                    notificationToSend.setContent("Blood bank don't have enough blood for " +
                            "scheduled supply right now. Please contact us for more information.");
                    bloodTemplate.convertAndSend(MQConfigNotification.EXCHANGE, MQConfigNotification.ROUTING_KEY, notificationToSend);
                }
            }
        }
    }

    BloodType getBloodType(int type){
        switch (type){
            case 0:
                return BloodType.APositive;
            case 1:
                return BloodType.ANegative;
            case 2:
                return BloodType.BPositive;
            case 3:
                return BloodType.BNegative;
            case 4:
                return BloodType.ABPositive;
            case 5:
                return BloodType.ABNegative;
            case 6:
                return BloodType.OPositive;
            default:
                return BloodType.ONegative;
        }
    }
}
