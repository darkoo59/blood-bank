package bloodcenter.utils;

import bloodcenter.address.Address;
import bloodcenter.address.AddressDTO;
import bloodcenter.branch_center.BranchCenter;
import bloodcenter.branch_center.dto.BranchCenterDTO;
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
}
