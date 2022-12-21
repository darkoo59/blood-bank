package bloodcenter.available_appointment.dto;

import bloodcenter.person.dto.PersonDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AvailableAppointmentsDTO {
    private long id;
    private String title;
    private String start;
    private String end;
}
