package bloodcenter.feedback.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class FeedbackDTO {
    private Long id;
    private String content;
    private LocalDateTime postedOn;
    private int grade;

    private FeedbackAuthorDTO user;
}
