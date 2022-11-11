package bloodcenter.utils;

import bloodcenter.address.Address;
import bloodcenter.address.AddressDTO;
import bloodcenter.branch_center.BranchCenter;
import bloodcenter.branch_center.dto.BranchCenterDTO;
import bloodcenter.user.dto.BCAdminDTO;
import bloodcenter.user.dto.RegisterDTO;
import bloodcenter.user.model.BCAdmin;
import bloodcenter.user.model.User;
import org.modelmapper.ModelMapper;

public class ObjectsMapper {
    private static ModelMapper modelMapper = new ModelMapper();

    public static BranchCenterDTO convertBranchCenterToDTO(BranchCenter branchCenter){
        BranchCenterDTO branchCenterDTO = modelMapper.map(branchCenter, BranchCenterDTO.class);
        branchCenterDTO.setAddress(convertAddressToDTO(branchCenter.getAddress()));
        return branchCenterDTO;
    }

    public static AddressDTO convertAddressToDTO(Address address){
        return modelMapper.map(address, AddressDTO.class);
    }

    public static BCAdminDTO convertBCAdminToDTO(BCAdmin admin) {
        BCAdminDTO bcAdminDTO = modelMapper.map(admin, BCAdminDTO.class);
        bcAdminDTO.setBranchCenter(admin.getBranchCenter());
        return bcAdminDTO;
    }

    public static User convertRegisterDTOToUser(RegisterDTO registerDTO) {
        return modelMapper.map(registerDTO, User.class);
    }
}
