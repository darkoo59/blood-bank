package bloodcenter.branch_center;

import bloodcenter.user.model.BCAdmin;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table
@NoArgsConstructor
public class BranchCenter {
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private Long id;
    private String name;
    private String description;

    @OneToMany(mappedBy = "branchCenter")
    private List<BCAdmin> admins;

    public BranchCenter(String name, String description) {
        this.name = name;
        this.description = description;
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<BCAdmin> getAdmins() {
        return admins;
    }

    public void setAdmins(List<BCAdmin> admins) {
        this.admins = admins;
    }
}
