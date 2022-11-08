package bloodcenter.address;

import lombok.Data;

@Data
public class AddressDTO {
    public Long id;
    public String street;
    public String number;
    public String city;
    public String country;

    public AddressDTO () {}

    public AddressDTO (Address a) {
        this.id = a.getId();
        this.street = a.getStreet();
        this.city = a.getCity();
        this.country = a.getCountry();
        this.number = a.getNumber();
    }
}
