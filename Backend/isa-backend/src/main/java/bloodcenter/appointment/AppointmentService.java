package bloodcenter.appointment;

import bloodcenter.appointment.dto.CreateAppointmentDTO;
import bloodcenter.available_appointment.AvailableAppointment;
import bloodcenter.available_appointment.AvailableAppointmentService;
import bloodcenter.person.model.User;
import bloodcenter.person.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
public class AppointmentService {
    private final AppointmentRepository repository;
    private final UserService userService;
    private final AvailableAppointmentService availableAppointmentService;
    @Autowired
    public AppointmentService(AppointmentRepository repository, UserService userService, AvailableAppointmentService availableAppointmentService){
        this.repository = repository;
        this.userService = userService;
        this.availableAppointmentService = availableAppointmentService;
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
    public boolean HaveYouGiveBloodLastSixMonths(long userId){
        User user = userService.getById(userId).get();
        LocalDateTime beforeSixMonths = LocalDateTime.now().minusMonths(6);
        List<Appointment>appointments = repository.getAppointmentByEndAfterAndUser(beforeSixMonths,user);
        if(appointments.size() == 0)
            return false;
        return true;
    }

    public void userCreateAppointment(CreateAppointmentDTO appointmentDTO) {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        LocalDateTime dateTime = LocalDateTime.parse(appointmentDTO.getSelectedDate(), formatter);
        AvailableAppointment selectedAvailableAppointment = availableAppointmentService.getByUserSelectedDateAndBcId(dateTime,
                appointmentDTO.getBranchCenterId());
        Appointment appointment = new Appointment();
        appointment.setBegin(selectedAvailableAppointment.getStart());
        appointment.setEnd(selectedAvailableAppointment.getEnd());
        appointment.setUser(userService.getById(appointmentDTO.getUserId()).get());
        repository.save(appointment);
        availableAppointmentService.remove(selectedAvailableAppointment);
    }
}
