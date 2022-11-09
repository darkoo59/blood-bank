package bloodcenter.branch_center;

import bloodcenter.address.Address;
import bloodcenter.user.model.BCAdmin;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Table
@NoArgsConstructor
@Data
public class BranchCenter {
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private Long id;
    private String name;
    private String description;

    @OneToOne
    @NotNull
    private Address address;

    @OneToMany(mappedBy = "branchCenter")
    @JsonIgnore
    private List<BCAdmin> admins;

    public BranchCenter(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public BranchCenter(String name, String description, Address address) {
        this.name = name;
        this.description = description;
        this.address = address;
    }

    public static class BCNotFoundException extends Exception{
        public BCNotFoundException(String message){
            super(message);
        }
    }

}
