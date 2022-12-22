package bloodcenter.donation.dto;

import bloodcenter.blood.BloodType;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DonationSimpleDTO {
    private long id;
    private BloodType bloodType;
    private String note;
    private String copperSulfate;
    private String hemoglobin;
    private Long normal;
    private Long low;
    private String lungs;
    private String heart;
    private Long tt;
    private Long tb;
    private double bloodAmount;
}
