package bloodcenter.branch_center.dto;

import bloodcenter.branch_center.BranchCenter;
import bloodcenter.core.Address;

public class BranchCenterDTO {
    public Long id;
    public String name;
    public String description;
    public Address address;

    public BranchCenterDTO(){}
    public BranchCenterDTO(BranchCenter bc){
        if (bc == null) return;
        this.id = bc.getId();
        this.name = bc.getName();
        this.description = bc.getDescription();
        this.address = bc.getAddress();
    }
}
