package bloodcenter.feedback;

import bloodcenter.branch_center.BranchCenter;
import bloodcenter.person.model.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Feedback {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String content;
    private LocalDateTime postedOn;
    private int grade;

    @ManyToOne
    private User user;

    @ManyToOne
    private BranchCenter branchCenter;

    public Feedback(String content, LocalDateTime postedOn, int grade) {
        this.content = content;
        this.postedOn = postedOn;
        this.grade = grade;
    }
}
