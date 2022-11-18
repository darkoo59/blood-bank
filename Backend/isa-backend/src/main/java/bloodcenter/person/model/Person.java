package bloodcenter.person.model;

import bloodcenter.address.Address;
import bloodcenter.person.enums.Sex;
import bloodcenter.role.Role;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@NoArgsConstructor
public abstract class Person {
    @Id
    @GeneratedValue(
            strategy = GenerationType.AUTO
    )
    protected Long id;
    protected String firstname;
    protected String lastname;
    protected String email;
    protected String password;
    @ManyToMany(fetch = FetchType.EAGER)
    protected Collection<Role> roles = new ArrayList<>();
    @ManyToOne
    @JoinColumn(name="address_id", referencedColumnName = "id")
    protected Address address;
    protected String phone;
    protected String nationalId;
    protected Sex sex;
    protected String occupation;
    protected String information;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Person person = (Person) o;
        return id != null && Objects.equals(id, person.id);
    }

    public Person(String firstname, String lastname, String email) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
    }

    public Person(String firstname, String lastname, String email, String password) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.password = password;
    }

    public Person(String firstname, String lastname, String email, String password, String phone, String nationalId, Sex sex, String occupation, String information) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.nationalId = nationalId;
        this.sex = sex;
        this.occupation = occupation;
        this.information = information;
    }

    public Long getId() {
        return id;
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    public String getPassword() { return password; }

    public void setPassword(String password) { this.password = password; }

    public static class PersonNotFoundException extends Exception {
        public PersonNotFoundException(String message){
            super(message);
        }
    }

    public static class PersonCantBeUpdatedException extends Exception {
        public PersonCantBeUpdatedException(String message){
            super(message);
        }
    }
}
