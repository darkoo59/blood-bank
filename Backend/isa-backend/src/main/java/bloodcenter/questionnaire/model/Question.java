package bloodcenter.questionnaire.model;

import bloodcenter.questionnaire.enums.QuestionType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity(name = "question")
@NoArgsConstructor
@Setter
@Getter
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne
    private Questionnaire questionnaire;
    private String text;
    private QuestionType type;

    public Question(String text, QuestionType type) {
        this.text = text;
        this.type = type;
    }
}
