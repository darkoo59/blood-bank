package bloodcenter.complaint.dto;

import bloodcenter.branch_center.BranchCenter;
import bloodcenter.branch_center.dto.BranchCenterDTO;
import bloodcenter.person.dto.PersonDTO;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ComplaintDTO {
    public Long id;
    public String text;
    public PersonDTO user;
    public BranchCenterDTO branchCenter;
}
