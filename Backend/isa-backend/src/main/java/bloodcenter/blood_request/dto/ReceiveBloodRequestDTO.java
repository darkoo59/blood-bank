package bloodcenter.blood_request.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ReceiveBloodRequestDTO {
    private int bloodType;
    private LocalDateTime finalDate;
    private boolean urgent;
    private float quantityInLiters;
}
