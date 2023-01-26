package bloodcenter.appointment;

import bloodcenter.person.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    List<Appointment> getAppointmentByEndAfterAndUserAndStarted(LocalDateTime date, User user, Boolean hasStarted);
    List<Appointment> getAllByUser_Id(long id);
    List<Appointment> getAllByUser_IdAndEndBefore(Long id, LocalDateTime date);
}
