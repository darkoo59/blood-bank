package bloodcenter.model;

import bloodcenter.enums.BloodType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
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
