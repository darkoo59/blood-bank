package bloodcenter.questionnaire.dto;

import bloodcenter.questionnaire.model.Question;
import lombok.Data;

import java.util.List;

public class QuestionnaireDTO {
    public List<AnswerDTO> answers;
    public long userId;
}
