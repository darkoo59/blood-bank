package bloodcenter.branch_center;

import bloodcenter.address.Address;
import bloodcenter.available_appointment.AvailableAppointment;
import bloodcenter.feedback.Feedback;
import bloodcenter.person.model.BCAdmin;
import bloodcenter.working_days.WorkingDay;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalTime;
import java.util.List;

@Entity
@NoArgsConstructor
@Setter
@Getter
public class BranchCenter {
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private Long id;
    private String name;
    private String description;
    private LocalTime startTime;
    private LocalTime endTime;
    @OneToOne
    @NotNull
    private Address address;

    @OneToOne
    private WorkingDay workingDays;

    @OneToMany(mappedBy = "branchCenter")
    @JsonIgnore
    private List<BCAdmin> admins;

    @OneToMany(mappedBy = "branchCenter")
    @JsonIgnore
    private List<Feedback> feedback;

    @OneToMany(mappedBy = "branchCenter")
    @JsonIgnore
    private List<AvailableAppointment> availableAppointments;

    public BranchCenter(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public BranchCenter(String name, String description, LocalTime start, LocalTime end) {
        this.name = name;
        this.description = description;
        this.startTime = start;
        this.endTime = end;
    }

    public BranchCenter(String name, String description, Address address, LocalTime start, LocalTime end, WorkingDay workingDays) {
        this.name = name;
        this.description = description;
        this.address = address;
        this.startTime = start;
        this.endTime = end;
        this.workingDays = workingDays;
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
