package bloodcenter.working_days;

import bloodcenter.branch_center.BranchCenter;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Setter
@Getter
public class WorkingDay {
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private Long id;
    private boolean monday;
    private boolean tuesday;
    private boolean wednesday;
    private boolean thursday;
    private boolean friday;
    private boolean saturday;
    private boolean sunday;

    public WorkingDay(boolean monday, boolean tuesday, boolean wednesday, boolean thursday, boolean friday, boolean saturday, boolean sunday) {
        this.monday = monday;
        this.tuesday = tuesday;
        this.wednesday = wednesday;
        this.thursday = thursday;
        this.friday = friday;
        this.saturday = saturday;
        this.sunday = sunday;
    }

    public List<Integer> generateIntegerList() {
        List<Integer> listToReturn = new ArrayList<>();
        if(monday)
            listToReturn.add(1);
        if(tuesday)
            listToReturn.add(2);
        if(wednesday)
            listToReturn.add(3);
        if(thursday)
            listToReturn.add(4);
        if(friday)
            listToReturn.add(5);
        if(saturday)
            listToReturn.add(6);
        if(sunday)
            listToReturn.add(7);

        return listToReturn;
    }
}
