package bloodcenter.complaint;

import bloodcenter.branch_center.BranchCenter;
import bloodcenter.person.model.BCAdmin;
import bloodcenter.person.model.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.transaction.Transactional;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Transactional
public class Complaint {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String text;

    @ManyToOne
    private User user;

    @ManyToOne
    private BCAdmin staff;

    @ManyToOne
    private BranchCenter branchCenter;

    private boolean replied;

    @Version
    private long Version;

    public Complaint(String text) {
        this.text = text;
        this.replied = false;
    }

    public Complaint(Long id, String text) {
        this.id = id;
        this.text = text;
        this.replied = false;
    }
}
