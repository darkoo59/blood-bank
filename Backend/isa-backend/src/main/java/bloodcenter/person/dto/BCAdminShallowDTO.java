package bloodcenter.person.dto;

import bloodcenter.address.AddressDTO;
import bloodcenter.person.enums.Sex;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BCAdminShallowDTO {
    private Long id;

    private String firstname;
    private String lastname;
    private String email;
    private String phone;
    private String nationalId;
    private Sex sex;
    private String occupation;
    private String information;

    private AddressDTO address;
}
