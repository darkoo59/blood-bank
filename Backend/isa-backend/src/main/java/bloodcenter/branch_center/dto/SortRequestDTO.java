package bloodcenter.branch_center.dto;

import lombok.Data;

import java.util.List;

@Data
public class SortRequestDTO {
    private List<BranchCenterDTO> centersList;
    private String sortBy;
    private boolean ascending;
}
