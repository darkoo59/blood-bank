package bloodcenter.person.dto;

import bloodcenter.address.AddressDTO;
import bloodcenter.branch_center.dto.BranchCenterDTO;
import bloodcenter.person.enums.Sex;
import bloodcenter.role.RoleDTO;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;

@Data
@NoArgsConstructor
public class BCAdminDTO {
    private Long id;

    private String firstname;
    private String lastname;
    private String email;
    private String phone;
    private String nationalId;
    private Sex sex;
    private String occupation;
    private String information;

    private BranchCenterDTO branchCenter;
    private Collection<RoleDTO> roles;
    private AddressDTO address;

}
