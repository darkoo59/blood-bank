package bloodcenter.address;

import lombok.Data;

@Data
public class AddressDTO {
    public Long id;
    public double lat;
    public double lng;
    public String street;
    public String number;
    public String city;
    public String country;

    public AddressDTO () {}
}
