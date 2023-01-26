package bloodcenter.available_appointment;

import bloodcenter.branch_center.BranchCenter;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
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
    @ManyToOne
    private BranchCenter branchCenter;
    @Version
    private long version;

    public AvailableAppointment(String title,LocalDateTime begin, LocalDateTime end){
        this.title = title;
        this.start = begin;
        this.end = end;
    }

    public AvailableAppointment(long id,String title,LocalDateTime begin, LocalDateTime end){
        this.id = id;
        this.title = title;
        this.start = begin;
        this.end = end;
    }
}
