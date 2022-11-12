package bloodcenter.role;

import lombok.*;

import javax.persistence.*;
import static javax.persistence.GenerationType.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Role {
    @Id
    @GeneratedValue(strategy = AUTO)
    private long Id;
    private String name;
}
