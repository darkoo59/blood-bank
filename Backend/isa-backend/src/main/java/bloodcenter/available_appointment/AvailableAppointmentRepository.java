package bloodcenter.available_appointment;

import bloodcenter.appointment.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Repository
public interface AvailableAppointmentRepository extends JpaRepository<AvailableAppointment, Long> {
    List<AvailableAppointment> getAvailableAppointmentByBranchCenterId(long branchCenterId);
    @Query("from AvailableAppointment t where t.start >= :startDate and t.end <= :endDate")
    List<AvailableAppointment> getAvailableAppointmentsBetweenDates(@Param("startDate") LocalDateTime startDate, @Param("endDate")LocalDateTime endDate);
}
