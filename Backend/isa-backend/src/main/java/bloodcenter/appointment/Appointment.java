package bloodcenter.appointment;

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

    private LocalDateTime begin;
    private LocalDateTime end;

    @ManyToOne
    @NotNull
    private User user;

    public Appointment(User user){
        this.user = user;
    }
}
