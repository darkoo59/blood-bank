package bloodcenter.user.dto;

import bloodcenter.user.enums.Sex;

public class RegisterDTO {
    public String name;
    public String surname;
    public String email;
    public String password;
    public String confirmPassword;
//    public Address address;
    public String phone;
    public String personalId;
    public Sex sex;

    public RegisterDTO() {
    }

    public RegisterDTO(RegisterDTO copy) {
        if (copy == null) return;

        this.name = copy.name;
        this.surname = copy.surname;
        this.email = copy.email;
        this.password = copy.password;
        this.confirmPassword = copy.confirmPassword;
        this.phone = copy.phone;
        this.personalId = copy.personalId;
        this.sex = copy.sex;
    }
}
