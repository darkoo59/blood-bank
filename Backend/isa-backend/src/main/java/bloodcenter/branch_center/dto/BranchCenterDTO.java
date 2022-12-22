package bloodcenter.branch_center.dto;

import bloodcenter.address.AddressDTO;
import bloodcenter.available_appointment.dto.AvailableAppointmentsDTO;
import bloodcenter.available_appointment.AvailableAppointment;
import bloodcenter.available_appointment.dto.BCAvailableAppointmentDTO;
import bloodcenter.feedback.dto.FeedbackDTO;
import bloodcenter.person.dto.BCAdminShallowDTO;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class BranchCenterDTO {
    public Long id;
    public String name;
    public String description;
    public AddressDTO address;
    public List<BCAdminShallowDTO> admins;
    public List<FeedbackDTO> feedback;
    private List<BCAvailableAppointmentDTO> availableAppointments;
}
