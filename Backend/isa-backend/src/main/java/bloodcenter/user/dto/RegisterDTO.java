package bloodcenter.user.dto;

import bloodcenter.address.AddressDTO;
import bloodcenter.user.enums.Sex;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RegisterDTO {
    public String firstname;
    public String lastname;
    public String email;
    public String password;
    public String confirmPassword;
    public AddressDTO address;
    public String phone;
    public String nationalId;
    public Sex sex;
    public String occupation;
    public String information;

    public RegisterDTO(RegisterDTO copy) {
        if (copy == null) return;

        this.firstname = copy.firstname;
        this.lastname = copy.lastname;
        this.email = copy.email;
        this.password = copy.password;
        this.confirmPassword = copy.confirmPassword;
        this.address = copy.address;
        this.phone = copy.phone;
        this.nationalId = copy.nationalId;
        this.sex = copy.sex;
        this.occupation = copy.occupation;
        this.information = copy.information;
    }
}
