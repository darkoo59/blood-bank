package bloodcenter.branch_center.dto;

import bloodcenter.branch_center.BranchCenter;

public class BranchCenterDTO {
    public Long id;
    public String name;
    public String description;

    public BranchCenterDTO(BranchCenter bc){
        if (bc == null) return;
        this.id = bc.getId();
        this.name = bc.getName();
        this.description = bc.getDescription();
    }
}
