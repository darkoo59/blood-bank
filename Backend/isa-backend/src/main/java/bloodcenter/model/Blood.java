package bloodcenter.model;

import bloodcenter.enums.BloodType;

import javax.persistence.*;

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

    public Blood() {
    }

    public Blood(BloodType type, Float quantity) {
        this.type = type;
        this.quantity = quantity;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BloodType getType() {
        return type;
    }

    public void setType(BloodType type) {
        this.type = type;
    }

    public Float getQuantity() {
        return quantity;
    }

    public void setQuantity(Float quantity) {
        this.quantity = quantity;
    }
}
