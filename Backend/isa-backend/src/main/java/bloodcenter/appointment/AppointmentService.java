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

    public Appointment GetById(long id){
        return repository.findById(id).get();
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
