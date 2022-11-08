package bloodcenter.user.dto;

import bloodcenter.branch_center.BranchCenter;
import bloodcenter.user.model.BCAdmin;

public class BCAdminDTO {
    public Long id;

    public String firstname;
    public String lastname;
    public String email;
    public BranchCenter branchCenter;

    public BCAdminDTO(BCAdmin a){
        if (a == null) return;

        this.id = a.getId();
        this.firstname = a.getFirstname();
        this.lastname = a.getLastname();
        this.email = a.getEmail();
        this.branchCenter = a.getBranchCenter();
    }

}
