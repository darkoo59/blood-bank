package bloodcenter.subscribed_hospitals.dto;

import bloodcenter.blood.BloodType;
import lombok.Data;

@Data
public class BloodToSendDTO {
    private String bloodType;
    private Float quantity;
}
