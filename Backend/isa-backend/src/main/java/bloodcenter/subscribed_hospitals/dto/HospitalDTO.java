package bloodcenter.subscribed_hospitals.dto;

import bloodcenter.blood.BloodType;
import lombok.Data;

import javax.persistence.Column;

@Data
public class HospitalDTO {
    private String hospitalName;
    private String email;
    private String server;
    private BloodType bloodType;
    private Float quantity;
}
