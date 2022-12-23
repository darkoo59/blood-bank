package bloodcenter.complaint.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ComplaintResponseDTO {
    public Long id;
    public String text;
}
