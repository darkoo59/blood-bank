package bloodcenter.utils;

import bloodcenter.address.Address;
import bloodcenter.address.AddressDTO;
import bloodcenter.branch_center.BranchCenter;
import bloodcenter.branch_center.dto.BranchCenterDTO;
import bloodcenter.feedback.Feedback;
import bloodcenter.feedback.dto.FeedbackAuthorDTO;
import bloodcenter.feedback.dto.FeedbackDTO;
import bloodcenter.person.dto.BCAdminDTO;
import bloodcenter.person.dto.BCAdminShallowDTO;
import bloodcenter.person.dto.PersonDTO;
import bloodcenter.person.dto.RegisterDTO;
import bloodcenter.person.model.Admin;
import bloodcenter.person.model.BCAdmin;
import bloodcenter.person.model.Person;
import bloodcenter.person.model.User;
import bloodcenter.role.Role;
import bloodcenter.role.RoleDTO;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;

public class ObjectsMapper {
    private static final ModelMapper modelMapper = new ModelMapper();

    public static BranchCenterDTO convertBranchCenterToDTO(BranchCenter branchCenter){
        BranchCenterDTO branchCenterDTO = modelMapper.map(branchCenter, BranchCenterDTO.class);
        branchCenterDTO.setAddress(convertAddressToDTO(branchCenter.getAddress()));
        ArrayList<BCAdminShallowDTO> admins = new ArrayList<>();
        for(BCAdmin a: branchCenter.getAdmins()){
            admins.add(convertBCAdminToShallowDTO(a));
        }
        branchCenterDTO.setAdmins(admins);
        ArrayList<FeedbackDTO> feedback = new ArrayList<>();
        for(Feedback f: branchCenter.getFeedback()){
            feedback.add(convertFeedbackToDTO(f));
        }
        branchCenterDTO.setFeedback(feedback);
        return branchCenterDTO;
    }

    public static List<PersonDTO> convertUserListToDTO(List<User> userList){
        ArrayList<PersonDTO> dtos = new ArrayList<>();
        for(User user: userList){
            dtos.add(convertPersonToDTO(user));
        }
        return dtos;
    }

    public static PersonDTO convertPersonToDTO(Person person) {
        return modelMapper.map(person,PersonDTO.class);
    }
    public static AddressDTO convertAddressToDTO(Address address){
        return modelMapper.map(address, AddressDTO.class);
    }

    public static Address convertDTOToAddress(AddressDTO addressDTO) { return modelMapper.map(addressDTO, Address.class); }
    public static BCAdminShallowDTO convertBCAdminToShallowDTO(BCAdmin admin) {
        BCAdminShallowDTO dto = modelMapper.map(admin, BCAdminShallowDTO.class);
        if(admin.getAddress() != null)
            dto.setAddress(convertAddressToDTO(admin.getAddress()));
        return dto;
    }

    public static BCAdminDTO convertBCAdminToDTO(BCAdmin admin){
        BCAdminDTO dto = modelMapper.map(admin, BCAdminDTO.class);
        if(admin.getBranchCenter() != null)
            dto.setBranchCenter(convertBranchCenterToDTO(admin.getBranchCenter()));
        if(admin.getAddress() != null)
            dto.setAddress(convertAddressToDTO(admin.getAddress()));
        return dto;
    }

    public static User convertRegisterDTOToUser(RegisterDTO registerDTO) {
        return modelMapper.map(registerDTO, User.class);
    }

    public static PersonDTO convertPersonToPersonDTO(Person person) {
        return modelMapper.map(person, PersonDTO.class);

    }

    public static Admin convertPersonToAdmin(Person person) {
        return modelMapper.map(person,Admin.class);
    }

    public static FeedbackDTO convertFeedbackToDTO(Feedback feedback){
        FeedbackDTO dto = modelMapper.map(feedback, FeedbackDTO.class);
        if(feedback.getUser() != null)
            dto.setUser(PersonToFeedbackAuthorDTO(feedback.getUser()));
        return dto;
    }

    public static FeedbackAuthorDTO PersonToFeedbackAuthorDTO(Person person){
        return modelMapper.map(person, FeedbackAuthorDTO.class);
    }
}
