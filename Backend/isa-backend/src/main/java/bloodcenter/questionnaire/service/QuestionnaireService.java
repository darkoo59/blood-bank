package bloodcenter.questionnaire.service;

import bloodcenter.person.model.User;
import bloodcenter.person.service.UserService;
import bloodcenter.questionnaire.dto.AnswerDTO;
import bloodcenter.questionnaire.dto.AnsweredQuestionnaireDTO;
import bloodcenter.questionnaire.dto.QuestionnaireDTO;
import bloodcenter.questionnaire.enums.QuestionType;
import bloodcenter.questionnaire.model.Answer;
import bloodcenter.questionnaire.model.Question;
import bloodcenter.questionnaire.repository.QuestionnaireRepository;
import bloodcenter.utils.ObjectsMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class QuestionnaireService {
    private final QuestionnaireRepository questionnaireRepository;
    private final QuestionService questionService;
    private final UserService userService;
    private final AnswerService answerService;

    public List<Question> getMaleQuestions(String name) {
        return questionService.getQuestionsByQuestionnaireNameAndTypeExcluding(name, QuestionType.FOR_FEMALE);
    }

    public List<Question> getFemaleQuestions(String name) {
        return questionService.getQuestionsByQuestionnaireNameAndTypeExcluding(name, QuestionType.FOR_MALE);
    }

    public AnsweredQuestionnaireDTO getAnsweredQuestionnaireByUserId(Long id) {
        var answers = answerService.getAnswersByUserId(id);
        if (answers.isEmpty()) return null;
        answers.sort(Comparator.comparing(Answer::getId));
        List<AnswerDTO> answersDTO = new ArrayList<>();
        List<Question> questions = new ArrayList<>();
        answers.forEach(answer -> {
            answersDTO.add(ObjectsMapper.convertAnswerToAnswerDTO(answer));
            questions.add(questionService.getQuestionById(answer.getQuestion().getId()));
        });

        return new AnsweredQuestionnaireDTO(questions, answersDTO);
    }

    public void submitQuestionnaire(QuestionnaireDTO dto) {
        for (var answer : dto.answers) {
            Optional<Question> optionalQuestion = questionService.getById(answer.questionId);
            if (optionalQuestion.isEmpty()) {
                return;
            }
            Question question = optionalQuestion.get();
            Optional<User> optionalUser = userService.getById(dto.userId);
            if (optionalUser.isEmpty()) {
                return;
            }
            User user = optionalUser.get();
            var ans = new Answer(question, user, answer.checked);

            var existingAnswer = answerService.getAnswerByUserIdAndQuestionId(user.getId(), answer.questionId);
            if (existingAnswer != null) {
                ans.setId(existingAnswer.getId());
                answerService.update(ans);
            } else {
                answerService.save(ans);
            }
        }
    }
}
