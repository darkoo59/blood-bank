package bloodcenter.questionnaire.model;

import bloodcenter.person.model.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity(name = "answer")
@NoArgsConstructor
@Setter
@Getter
public class Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @OneToOne
    private Question question;
    @ManyToOne
    private User user;
    private boolean checked;

    public Answer(Question question, User user, boolean checked) {
        this.question = question;
        this.user = user;
        this.checked = checked;
    }
}
