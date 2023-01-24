package bloodcenter.blood_request.model;

import bloodcenter.blood.BloodType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Setter
@Getter
@Table(name="blood_request")
public class BloodRequest {
    @Column(name="id")
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private Long id;
    @Column(name="blood_type")
    private BloodType bloodType;
    @Column(name="quantity")
    private float quantity;
    @Column(name="final_date")
    private LocalDateTime finalDate;
}
