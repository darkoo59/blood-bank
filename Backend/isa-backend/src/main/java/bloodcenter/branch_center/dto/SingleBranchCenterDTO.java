package bloodcenter.branch_center.dto;

import bloodcenter.address.AddressDTO;
import bloodcenter.available_appointment.dto.AvailableAppointmentsDTO;
import bloodcenter.feedback.dto.FeedbackDTO;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class SingleBranchCenterDTO {
    public String name;
    public String description;
    public WorkingHoursDTO workTime;
    public AddressDTO address;
    public WorkingDaysDTO workingDays;
    public List<FeedbackDTO> feedback;
    public List<AvailableAppointmentsDTO> availableAppointments;
}
