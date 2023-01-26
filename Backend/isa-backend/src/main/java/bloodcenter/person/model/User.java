package bloodcenter.person.model;

import bloodcenter.appointment.Appointment;
import bloodcenter.blood.BloodType;
import bloodcenter.donation.Donation;
import bloodcenter.person.enums.Sex;
import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
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

    @OneToMany(mappedBy = "user")
    private List<Donation> donations;
    private UserRank rank;
    private int points;


    private int penalties;

    public void addPenalty(){
        penalties++;
    }

    public User(String firstname, String lastname, String email, String password) {
        super(firstname, lastname, email, password);
    }

    public User(String firstname, String lastname, String email, String password, String phone, String nationalId, Sex sex, String occupation, String information, UserRank rank, int points)
    {
        super(firstname,lastname,email,password,phone,nationalId,sex,occupation,information);
        this.rank = rank;
        this.points = points;
    }

    public User(String firstname, String lastname, String email, String password, String phone, String nationalId, Sex sex, String occupation, String information)
    {
        super(firstname,lastname,email,password,phone,nationalId,sex,occupation,information);
    }
}
