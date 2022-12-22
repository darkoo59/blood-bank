package bloodcenter.working_days;

import bloodcenter.feedback.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkingDaysRepository extends JpaRepository<WorkingDay, Long> {
}
