package bloodcenter.utils;

import bloodcenter.address.Address;
import bloodcenter.address.AddressDTO;
import bloodcenter.branch_center.BranchCenter;
import bloodcenter.branch_center.dto.BranchCenterDTO;
import bloodcenter.person.dto.BCAdminDTO;
import bloodcenter.person.dto.PersonDTO;
import bloodcenter.person.dto.RegisterDTO;
import bloodcenter.person.model.BCAdmin;
import bloodcenter.person.model.Person;
import bloodcenter.person.model.User;
import org.modelmapper.ModelMapper;

public class ObjectsMapper {
    private static ModelMapper modelMapper = new ModelMapper();

    public static BranchCenterDTO convertBranchCenterToDTO(BranchCenter branchCenter){
        BranchCenterDTO branchCenterDTO = modelMapper.map(branchCenter, BranchCenterDTO.class);
        branchCenterDTO.setAddress(convertAddressToDTO(branchCenter.getAddress()));
        return branchCenterDTO;
    }

    public static PersonDTO convertPersonToDTO(Person person) { return modelMapper.map(person,PersonDTO.class); }
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
