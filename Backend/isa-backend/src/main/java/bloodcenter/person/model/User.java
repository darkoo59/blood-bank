package bloodcenter.person.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;

@Entity(name = "user_")
@NoArgsConstructor
@Setter
@Getter
public class User extends Person
{
    public User(String firstname, String lastname, String email, String password) {
        super(firstname, lastname, email, password);
    }
}
