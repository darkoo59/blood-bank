package bloodcenter.user.model;

import bloodcenter.branch_center.BranchCenter;
import bloodcenter.role.Role;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.util.ArrayList;
import java.util.Collection;

@Entity(name = "bc_admin")
@NoArgsConstructor
@Data
public class BCAdmin extends Person {

    @ManyToOne
    private BranchCenter branchCenter;

    public BCAdmin(String firstname, String lastname, String email, String password) {
        super(firstname, lastname, email, password, "ROLE_ADMIN");
    }

    public static class BCAdminNotFoundException extends Exception {
        public BCAdminNotFoundException(String message){
            super(message);
        }
    }
}
