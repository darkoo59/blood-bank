package bloodcenter.branch_center.dto;

import lombok.Data;

import java.time.LocalTime;

@Data
public class WorkingHoursDTO {
    private LocalTime startTime;
    private LocalTime endTime;
}
