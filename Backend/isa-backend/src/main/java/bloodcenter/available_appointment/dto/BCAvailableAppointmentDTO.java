package bloodcenter.available_appointment.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BCAvailableAppointmentDTO {
    private long id;
    private String title;
    private LocalDateTime start;
    private LocalDateTime end;
}
