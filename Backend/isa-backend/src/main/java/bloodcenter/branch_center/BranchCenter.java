package bloodcenter.branch_center;

import bloodcenter.address.Address;
import bloodcenter.user.model.BCAdmin;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.NoArgsConstructor;

import javax.persistence.*;
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

    @OneToOne
    private Address address;

    @OneToMany(mappedBy = "branchCenter")
    @JsonIgnore
    private List<BCAdmin> admins;

    public BranchCenter(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public static class BCNotFoundException extends Exception{
        public BCNotFoundException(String message){
            super(message);
        }
    }

    public Long getId() {
        return id;
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

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
}