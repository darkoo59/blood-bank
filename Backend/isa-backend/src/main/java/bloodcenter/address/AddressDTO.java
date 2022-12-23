package bloodcenter.address;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddressDTO {
    public Long id;
    public double lat;
    public double lng;
    public String street;
    public String number;
    public String city;
    public String country;
}
