package bloodcenter.person.model;

import bloodcenter.appointment.Appointment;
import bloodcenter.person.enums.Sex;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.List;

@Entity(name = "user_")
@NoArgsConstructor
@Setter
@Getter
public class User extends Person
{
    @OneToMany(mappedBy = "user")
    private List<Appointment> appointments;

    public User(String firstname, String lastname, String email, String password) {
        super(firstname, lastname, email, password);
    }

    public User(String firstname, String lastname, String email, String password, String phone, String nationalId, Sex sex, String occupation, String information)
    {
        super(firstname,lastname,email,password,phone,nationalId,sex,occupation,information);
    }
}
