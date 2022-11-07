package bloodcenter.user.model;

import bloodcenter.branch_center.BranchCenter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity(name = "bc_admin")
@NoArgsConstructor
public class BCAdmin extends Person {

    @ManyToOne
    private BranchCenter branchCenter;

    public BCAdmin(String firstname, String lastname, String email) {
        super(firstname, lastname, email);
    }

    public BranchCenter getBranchCenter() {
        return branchCenter;
    }

    public void setBranchCenter(BranchCenter branchCenter) {
        this.branchCenter = branchCenter;
    }
}
