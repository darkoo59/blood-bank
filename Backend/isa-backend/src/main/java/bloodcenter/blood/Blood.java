package bloodcenter.blood;

import lombok.*;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Setter
@Getter
public class Blood {
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private Long id;
    private BloodType type;
    private Float quantity;

    public Blood(BloodType type, Float quantity) {
        this.type = type;
        this.quantity = quantity;
    }
}
