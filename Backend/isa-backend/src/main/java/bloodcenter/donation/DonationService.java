package bloodcenter.donation;

import bloodcenter.appointment.Appointment;
import bloodcenter.appointment.AppointmentService;
import bloodcenter.donation.dto.CreateDonationDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DonationService {
    private final DonationRepository repository;
    private final AppointmentService appointmentService;

    @Autowired
    public DonationService(DonationRepository repository, AppointmentService appointmentService){
        this.repository = repository;
        this.appointmentService = appointmentService;
    }

    public void createDonation(CreateDonationDTO dto) throws Exception {
        Appointment app = appointmentService.getById(dto.getAppointmentId());
        if(app == null) throw new Exception("Appointment not found.");
        if(!app.isStarted()) throw new Exception("Appointment has not been started yet.");
        if(app.getDonation() != null) throw new Exception("Appointment has already been finished.");

        Donation donation = new Donation(dto);
        donation.setUser(app.getUser());
        donation.setAppointment(app);

        repository.save(donation);
    }
}
