package bloodcenter.urgent_order.utils;

import bloodcenter.address.Address;
import bloodcenter.address.AddressDTO;
import bloodcenter.appointment.Appointment;
import bloodcenter.appointment.dto.AppointmentDTO;
import bloodcenter.available_appointment.AvailableAppointment;
import bloodcenter.available_appointment.dto.AvailableAppointmentsDTO;
import bloodcenter.branch_center.BranchCenter;
import bloodcenter.branch_center.dto.BranchCenterDTO;
import bloodcenter.donation.Donation;
import bloodcenter.donation.dto.DonationSimpleDTO;
import bloodcenter.feedback.Feedback;
import bloodcenter.feedback.dto.FeedbackAuthorDTO;
import bloodcenter.feedback.dto.FeedbackDTO;
import bloodcenter.person.dto.*;
import bloodcenter.person.model.Admin;
import bloodcenter.person.model.BCAdmin;
import bloodcenter.person.model.Person;
import bloodcenter.person.model.User;
import bloodcenter.questionnaire.dto.AnswerDTO;
import bloodcenter.questionnaire.model.Answer;
import bloodcenter.subscribed_hospitals.dto.HospitalDTO;
import bloodcenter.subscribed_hospitals.model.SubscribedHospital;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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

    public static AvailableAppointmentsDTO convertAvailableAppointmentToDTO(AvailableAppointment appointment) {
        return modelMapper.map(appointment, AvailableAppointmentsDTO.class);}

    public static AvailableAppointment convertDTOToAvailableAppointment(AvailableAppointmentsDTO dto){
        AvailableAppointment appointment = new AvailableAppointment();
        appointment.setStart(LocalDateTime.parse(dto.getStart(), DateTimeFormatter.ISO_OFFSET_DATE_TIME));
        appointment.setEnd(LocalDateTime.parse(dto.getEnd(), DateTimeFormatter.ISO_OFFSET_DATE_TIME));
        appointment.setTitle(dto.getTitle());
        return appointment;
    }

    public static PersonDTO convertPersonToDTO(Person person) {
        PersonDTO dto = modelMapper.map(person, PersonDTO.class);
        if(person.getAddress() != null)
            dto.setAddress(convertAddressToDTO(person.getAddress()));
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
        modelMapper.addMappings(new PropertyMap<RegisterDTO, User>() {
            protected void configure() {
                skip(destination.getId());
            }
        });
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

    public static SubscribedHospital convertHospitalDTOToSubscribedHospital(HospitalDTO hospitalDTO){
        return modelMapper.map(hospitalDTO, SubscribedHospital.class);
    }

    public static AppointmentDTO convertAppointmentToDTO(Appointment appointment){
        PersonDTO pdto = convertPersonToDTO(appointment.getUser());
        AppointmentDTO dto = modelMapper.map(appointment, AppointmentDTO.class);
        dto.setUser(pdto);

        if(appointment.getDonation() != null) {
            DonationSimpleDTO sdto = convertDonationToSimpleDTO(appointment.getDonation());
            dto.setDonation(sdto);
        }
        return dto;
    }

    public static DonationSimpleDTO convertDonationToSimpleDTO(Donation donation) {
        return modelMapper.map(donation, DonationSimpleDTO.class);
    }

    public static List<AppointmentDTO> convertAppointmentListToDTO(List<Appointment> list){
        ArrayList<AppointmentDTO> dtos = new ArrayList<>();
        for(Appointment app: list)
            dtos.add(convertAppointmentToDTO(app));
        return dtos;
    }

    public static AnswerDTO convertAnswerToAnswerDTO(Answer answer) {
        ModelMapper modelMapper = new ModelMapper();
        PropertyMap<Answer, AnswerDTO> answerMap = new PropertyMap<>() {
            protected void configure() {
                map().setChecked(source.isChecked());
                map().setQuestionId(source.getQuestion().getId());
            }
        };

        modelMapper.addMappings(answerMap);
        return modelMapper.map(answer, AnswerDTO.class);
    }

    public static Admin convertRegisterAdminDTOToAdmin(RegisterAdminDTO registerAdminDTO) {
        return modelMapper.map(registerAdminDTO, Admin.class);
    }
}
