package bloodcenter.branch_center.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WorkingHoursDTO {
    private LocalTime startTime;
    private LocalTime endTime;
}
