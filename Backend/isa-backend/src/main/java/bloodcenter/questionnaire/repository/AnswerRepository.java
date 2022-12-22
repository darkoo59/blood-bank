package bloodcenter.questionnaire.repository;

import bloodcenter.questionnaire.model.Answer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AnswerRepository extends JpaRepository<Answer, Long> {
    List<Answer> findByUser_Id(Long id);
    Optional<Answer> findAnswerByUser_IdAndQuestion_Id(Long userId, Long questionId);
}
