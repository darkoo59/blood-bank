package bloodcenter.person.model;

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

    public Admin(String firstname, String lastname, String email, String password) {
        super(firstname, lastname, email, password);
    }
}
