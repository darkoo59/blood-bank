package bloodcenter.appointment.dto;

import bloodcenter.person.dto.PersonDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentDTO {
    private long id;

    private LocalDateTime begin;
    private LocalDateTime end;

    private PersonDTO user;
}
