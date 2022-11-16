package bloodcenter.person.model;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;

@Entity
@NoArgsConstructor
public class Admin extends Person {

    public Admin(String firstname, String lastname, String email, String password) {
        super(firstname, lastname, email, password);
    }
}
