package bloodcenter.donation.dto;

import bloodcenter.blood.BloodType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateDonationDTO {
    private long appointmentId;
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
