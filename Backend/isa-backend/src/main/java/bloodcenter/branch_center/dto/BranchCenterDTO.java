package bloodcenter.branch_center.dto;

import bloodcenter.branch_center.BranchCenter;
import bloodcenter.address.AddressDTO;
import bloodcenter.core.Address;
import lombok.Data;

@Data
public class BranchCenterDTO {
    public Long id;
    public String name;
    public String description;
    public AddressDTO address;

    public BranchCenterDTO(){}
    public BranchCenterDTO(BranchCenter bc){
        if (bc == null) return;
        this.id = bc.getId();
        this.name = bc.getName();
        this.description = bc.getDescription();
        this.address = new AddressDTO(bc.getAddress());
    }
}
