package bloodcenter.appointment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AppointmentService {
    private final AppointmentRepository repository;
    @Autowired
    public AppointmentService(AppointmentRepository repository){
        this.repository = repository;
    }

    public Appointment GetById(long id){
        return repository.findById(id).get();
    }
}
