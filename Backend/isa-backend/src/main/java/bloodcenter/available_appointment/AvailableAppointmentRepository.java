package bloodcenter.available_appointment;

import bloodcenter.appointment.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AvailableAppointmentRepository extends JpaRepository<AvailableAppointment, Long> {
    List<AvailableAppointment> getAvailableAppointmentByBranchCenterId(long branchCenterId);
}
