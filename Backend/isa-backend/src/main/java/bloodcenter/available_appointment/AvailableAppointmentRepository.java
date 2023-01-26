package bloodcenter.available_appointment;

import bloodcenter.appointment.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.persistence.LockModeType;
import javax.persistence.QueryHint;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Repository
public interface AvailableAppointmentRepository extends JpaRepository<AvailableAppointment, Long> {
    List<AvailableAppointment> getAvailableAppointmentByBranchCenterId(long branchCenterId);

    @Query("from AvailableAppointment t where t.start >= :startDate and t.end <= :endDate")
    //@Lock(LockModeType.PESSIMISTIC_WRITE)
    //@QueryHints({@QueryHint(name = "javax.persistence.lock.timeout", value ="0")})
    List<AvailableAppointment> getAvailableAppointmentsBetweenDates(@Param("startDate") LocalDateTime startDate, @Param("endDate")LocalDateTime endDate);
}
