package bloodcenter.address;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@NoArgsConstructor
@Data
public class Address {
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private Long id;
    private String street;
    private String number;
    private String city;
    private String country;

    public Address(String street, String number, String city, String country) {
        this.street = street;
        this.number = number;
        this.city = city;
        this.country = country;
    }
}
