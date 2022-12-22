package bloodcenter.appointment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AppointmentService {
    private final AppointmentRepository repository;
    @Autowired
    public AppointmentService(AppointmentRepository repository){
        this.repository = repository;
    }

    public Appointment getById(long id) throws Exception {
        Optional<Appointment> app = repository.findById(id);
        if(app.isEmpty()) throw new Exception("Appointment not found.");
        return app.get();
    }

    public void startAppointment(long id) {
        Optional<Appointment> appointment = repository.findById(id);
        if(appointment.isEmpty()) return;

        appointment.get().setStarted(true);
        repository.save(appointment.get());
    }

    public List<Appointment> getAll(){
        return repository.findAll();
    }

    public void deleteAppointment(long id){
        Optional<Appointment> appointment = repository.findById(id);
        if(appointment.isEmpty() || appointment.get().getDonation() != null) return;
        appointment.get().getUser().addPenalty();
        repository.deleteById(id);

    }
}
