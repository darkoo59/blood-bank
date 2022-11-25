package bloodcenter.questionnaire.service;

import bloodcenter.person.model.User;
import bloodcenter.person.repository.UserRepository;
import bloodcenter.questionnaire.dto.QuestionnaireDTO;
import bloodcenter.questionnaire.enums.QuestionType;
import bloodcenter.questionnaire.model.Answer;
import bloodcenter.questionnaire.model.Question;
import bloodcenter.questionnaire.repository.AnswerRepository;
import bloodcenter.questionnaire.repository.QuestionRepository;
import bloodcenter.questionnaire.repository.QuestionnaireRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class QuestionnaireService {
    private final QuestionnaireRepository questionnaireRepository;
    private final QuestionRepository questionRepository;

    private final UserRepository userRepository;
    private final AnswerRepository answerRepository;

    public QuestionnaireService(QuestionnaireRepository questionnaireRepository, QuestionRepository questionRepository, UserRepository userRepository, AnswerRepository answerRepository) {
        this.questionnaireRepository = questionnaireRepository;
        this.questionRepository = questionRepository;
        this.userRepository = userRepository;
        this.answerRepository = answerRepository;
    }

    public List<Question> getMaleQuestions(String name) {
        return questionRepository.findQuestionByQuestionnaire_NameAndTypeIsNot(name, QuestionType.FOR_FEMALE);
    }

    public List<Question> getFemaleQuestions(String name) {
        return questionRepository.findQuestionByQuestionnaire_NameAndTypeIsNot(name, QuestionType.FOR_MALE);
    }

    public void submitQuestionnaire(QuestionnaireDTO dto) {
        for (var answer : dto.answers) {
            Optional<Question> optionalQuestion = questionRepository.findById(answer.questionId);
            if (optionalQuestion.isEmpty()) {
                return;
            }
            Question question = optionalQuestion.get();
            Optional<User> optionalUser = userRepository.findById(dto.userId);
            if (optionalUser.isEmpty()) {
                return;
            }
            User user = optionalUser.get();
            answerRepository.save(new Answer(question, user, answer.checked));
        }
    }
}
