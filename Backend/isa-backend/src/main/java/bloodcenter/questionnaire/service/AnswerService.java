package bloodcenter.questionnaire.service;

import bloodcenter.questionnaire.model.Answer;
import bloodcenter.questionnaire.repository.AnswerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class AnswerService {
    private final AnswerRepository answerRepository;

    public void save(Answer answer) {
        answerRepository.save(answer);
    }

    public void update(Answer answer) {
        Answer ans = answerRepository.findById(answer.getId()).orElse(null);
        if (ans != null ) {
            ans.setChecked(answer.isChecked());
            answerRepository.save(ans);
        }
    }

    public List<Answer> getAnswersByUserId(Long id) {
        return answerRepository.findByUser_Id(id);
    }

    public Answer getAnswerByUserIdAndQuestionId(Long userId, Long questionId) {
        var answer = answerRepository.findAnswerByUser_IdAndQuestion_Id(userId, questionId);
        if (answer.isEmpty()) {
            return null;
        }
        return answer.get();
    }
}
