package bloodcenter.appointment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AppointmentService {
    private final AppointmentRepository repository;
    @Autowired
    public AppointmentService(AppointmentRepository repository){
        this.repository = repository;
    }

    public Appointment getById(long id) {
        return repository.findById(id).get();
    }

    public Appointment getStartableAppointmentById(long id) throws Exception {
        Optional<Appointment> appointment = repository.findById(id);
        if(appointment.isEmpty()) return null;

        if(appointment.get().getDonation() != null)
            throw new Exception("Appointment has already been finished.");

        return appointment.get();
    }

    public void startAppointment(long id) {
        Optional<Appointment> appointment = repository.findById(id);
        if(appointment.isEmpty()) return;

        appointment.get().setStarted(true);
        repository.save(appointment.get());
    }
}
