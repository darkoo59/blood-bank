package bloodcenter.subscribed_hospitals.model;

import bloodcenter.blood.BloodType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Setter
@Getter
@Table(name="subscribed_hospital")
public class SubscribedHospital {
    @Column(name="id")
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private Long id;
    @Column(name="hospital_name")
    private String hospitalName;
    @Column(name="email",unique = true)
    private String email;
    @Column(name="server")
    private String server;
    @Column(name="blood_type")
    private BloodType bloodType;
    @Column(name="quantity")
    private Float quantity;

    public static class HospitalNotFoundException extends Exception{
        public HospitalNotFoundException(String message){
            super(message);
        }
    }
}
