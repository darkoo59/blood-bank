package bloodcenter.donation;

import bloodcenter.appointment.Appointment;
import bloodcenter.blood.BloodType;
import bloodcenter.donation.dto.CreateDonationDTO;
import bloodcenter.person.model.User;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Setter
@Getter
@AllArgsConstructor
public class Donation {
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
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

    @ManyToOne
    @NotNull
    private User user;

    @OneToOne
    private Appointment appointment;

    public Donation(CreateDonationDTO dto) throws InvalidDataException {
        bloodType = dto.getBloodType();
        note = dto.getNote();
        copperSulfate = dto.getCopperSulfate();
        hemoglobin = dto.getHemoglobin();
        normal = dto.getNormal();
        low = dto.getLow();
        lungs = dto.getLungs();
        heart = dto.getHeart();
        tt = dto.getTt();
        tb = dto.getTb();
        bloodAmount = dto.getBloodAmount();

        validate();
    }

    private void validate() throws InvalidDataException {
        if(bloodType == null || bloodAmount <= 0)
            throw new InvalidDataException();
    }

    public static class InvalidDataException extends Exception {
        public InvalidDataException() {
            super("Provided data is invalid.");
        }
    }
}
