package bloodcenter.branch_center.dto;

import bloodcenter.address.Address;
import bloodcenter.address.AddressDTO;
import bloodcenter.branch_center.BranchCenter;
import lombok.Data;

@Data
public class RegisterBranchCenterDTO {

    public String name;
    public String description;

    public AddressDTO address;

}
