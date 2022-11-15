package bloodcenter.person.dto;

import bloodcenter.role.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import java.util.ArrayList;
import java.util.Collection;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PersonDTO {
    protected Long id;
    protected String firstname;
    protected String lastname;
    protected String email;
}
