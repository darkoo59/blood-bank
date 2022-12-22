package bloodcenter.questionnaire.service;

import bloodcenter.questionnaire.enums.QuestionType;
import bloodcenter.questionnaire.model.Question;
import bloodcenter.questionnaire.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class QuestionService {
    private final QuestionRepository questionRepository;

    public List<Question> getQuestionsByQuestionnaireNameAndTypeExcluding(String name, QuestionType type) {
        return questionRepository.findQuestionByQuestionnaire_NameAndTypeIsNot(name, type);
    }

    public Optional<Question> getById(Long id) {
        return questionRepository.findById(id);
    }

    public Question getQuestionById(Long id) {
        return questionRepository.findById(id).orElse(null);
    }
}
