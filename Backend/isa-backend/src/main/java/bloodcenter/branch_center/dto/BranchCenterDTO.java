package bloodcenter.branch_center.dto;

import bloodcenter.address.AddressDTO;
import lombok.Data;

@Data
public class BranchCenterDTO {
    public Long id;
    public String name;
    public String description;
    public AddressDTO address;

    public BranchCenterDTO(){}
}
