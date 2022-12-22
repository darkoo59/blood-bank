package bloodcenter.appointment.dto;

import lombok.Data;

@Data
public class CreateAppointmentDTO {
    private String selectedDate;
    private long userId;
    private long branchCenterId;
}
