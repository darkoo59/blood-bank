package bloodcenter.user.model;

import lombok.NoArgsConstructor;

import javax.persistence.Entity;

@Entity(name = "user_")
@NoArgsConstructor
public class User extends Person
{
    public User(String firstname, String lastname, String email) {
        super(firstname, lastname, email);
    }
}
