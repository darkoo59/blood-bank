package bloodcenter.branch_center.dto;

import bloodcenter.branch_center.BranchCenter;
import lombok.Data;

@Data
public class RegisterBranchCenterDTO {

    public String name;
    public String description;
    public String country;
    public String city;
    public String street;
    public String number;

}
