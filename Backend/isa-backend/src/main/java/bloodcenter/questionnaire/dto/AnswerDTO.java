package bloodcenter.questionnaire.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AnswerDTO {
    public long questionId;
    public boolean checked;
}
