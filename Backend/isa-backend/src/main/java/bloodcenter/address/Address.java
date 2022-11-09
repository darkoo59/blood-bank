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
    private double lat;
    private double lng;
    private String street;
    private String number;
    private String city;
    private String country;

    public Address(double lat, double lng, String street, String number, String city, String country) {
        this.lat = lat;
        this.lng = lng;
        this.street = street;
        this.number = number;
        this.city = city;
        this.country = country;
    }
}
