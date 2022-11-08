package bloodcenter.utils;

import bloodcenter.branch_center.BranchCenter;
import bloodcenter.branch_center.dto.BranchCenterDTO;
import org.modelmapper.ModelMapper;

public class ObjectsMapper {
    private static ModelMapper modelMapper = new ModelMapper();

    public static BranchCenterDTO convertBranchCenterToDTO(BranchCenter branchCenter){
        BranchCenterDTO branchCenterDTO = modelMapper.map(branchCenter, BranchCenterDTO.class);
        branchCenterDTO.setAddress(branchCenter.getAddress());
        return branchCenterDTO;
    }
}
