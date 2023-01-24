package bloodcenter.appointment;

import bloodcenter.donation.Donation;
import bloodcenter.person.model.User;
import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Setter
@Getter
public class Appointment {
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private long id;
    private String title;
    private LocalDateTime begin;
    private LocalDateTime end;
    private boolean started;

    @ManyToOne
    @NotNull
    private User user;

    @OneToOne(mappedBy = "appointment")
    private Donation donation;

    public Appointment(User user){
        this.user = user;
    }

    public Appointment(LocalDateTime begin, LocalDateTime end)
    {
        this.begin = begin;
        this.end = end;
    }
}
