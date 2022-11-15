package bloodcenter.person.model;

import bloodcenter.branch_center.BranchCenter;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity(name = "bc_admin")
@NoArgsConstructor
@Getter
@Setter
public class BCAdmin extends Person {

    @ManyToOne
    private BranchCenter branchCenter;

    public BCAdmin(String firstname, String lastname, String email, String password) { super(firstname, lastname, email, password); }
    public BranchCenter getBranchCenter() {
        return branchCenter;
    }

    public void setBranchCenter(BranchCenter branchCenter) {
        this.branchCenter = branchCenter;
    }

    public static class BCAdminNotFoundException extends Exception {
        public BCAdminNotFoundException(String message){
            super(message);
        }
    }

    public static class BCAdminEmailTakenException extends Exception {
        public BCAdminEmailTakenException(String message) { super(message); }
    }

}
