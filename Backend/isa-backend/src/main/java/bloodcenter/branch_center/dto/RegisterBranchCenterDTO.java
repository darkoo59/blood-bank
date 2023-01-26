package bloodcenter.branch_center.dto;

import bloodcenter.address.Address;
import bloodcenter.address.AddressDTO;
import bloodcenter.branch_center.BranchCenter;
import lombok.Data;

import java.time.LocalTime;

@Data
public class RegisterBranchCenterDTO {

    public String name;
    public String description;
    public AddressDTO address;
    public boolean monday;
    public boolean tuesday;
    public boolean wednesday;
    public boolean thursday;
    public boolean friday;
    public boolean saturday;
    public boolean sunday;
    private LocalTime startTime;
    private LocalTime endTime;

}
