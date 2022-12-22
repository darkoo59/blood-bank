package bloodcenter.questionnaire.dto;

import bloodcenter.questionnaire.model.Question;

import java.util.List;

public class AnsweredQuestionnaireDTO {
    public List<Question> questions;
    public List<AnswerDTO> answers;

    public AnsweredQuestionnaireDTO(List<Question> questions, List<AnswerDTO> answers) {
        this.questions = questions;
        this.answers = answers;
    }
}
