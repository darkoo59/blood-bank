package bloodcenter.available_appointment;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Setter
@Getter
public class AvailableAppointment {
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private long id;
    private String title;
    private LocalDateTime start;
    private LocalDateTime end;
    private long branchCenterId;

    public AvailableAppointment(String title,LocalDateTime begin, LocalDateTime end, long branchCenterId){
        this.title = title;
        this.start = begin;
        this.end = end;
        this.branchCenterId = branchCenterId;
    }
}
