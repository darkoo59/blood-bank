package bloodcenter.person.model;

import bloodcenter.person.enums.Sex;
import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;

@Entity
@Setter
@Getter
@NoArgsConstructor
public class Admin extends Person {

    private boolean passwordChanged;
    public Admin(String firstname,
                 String lastname,
                 String email,
                 String password,
                 String phone,
                 String nationalId,
                 Sex sex,
                 String occupation,
                 String information)
    {
        super(firstname, lastname, email, password, phone, nationalId, sex, occupation,information);
        passwordChanged = false;
        enabled = true;
    }

    public Admin(String firstname,
                 String lastname,
                 String email,
                 String password,
                 String phone,
                 String nationalId,
                 Sex sex,
                 String occupation,
                 String information,
                 boolean passwordChanged)
    {
        super(firstname, lastname, email, password, phone, nationalId, sex, occupation,information);
        this.passwordChanged = passwordChanged;
        enabled = true;
    }


}
