package bloodcenter.questionnaire.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity(name = "questionnaire")
@NoArgsConstructor
@Setter
@Getter
public class Questionnaire {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
}
