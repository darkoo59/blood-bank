package bloodcenter.person.dto;

import bloodcenter.address.AddressDTO;
import bloodcenter.person.enums.Sex;

public class RegisterDTO {
    public String name;
    public String surname;
    public String email;
    public String password;
    public String confirmPassword;
    public AddressDTO address;
    public String phone;
    public String nationalId;
    public Sex sex;
    public String occupation;
    public String information;

    public RegisterDTO() {
    }

    public RegisterDTO(RegisterDTO copy) {
        if (copy == null) return;

        this.name = copy.name;
        this.surname = copy.surname;
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
