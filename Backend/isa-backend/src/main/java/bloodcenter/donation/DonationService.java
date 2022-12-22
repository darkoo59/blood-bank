package bloodcenter.donation;

import bloodcenter.appointment.Appointment;
import bloodcenter.appointment.AppointmentService;
import bloodcenter.blood.BloodService;
import bloodcenter.donation.dto.CreateDonationDTO;
import bloodcenter.questionnaire.model.Answer;
import bloodcenter.questionnaire.service.AnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DonationService {
    private final DonationRepository repository;
    private final AppointmentService appointmentService;
    private final BloodService bloodService;
    private final AnswerService answerService;

    @Autowired
    public DonationService(DonationRepository repository,
                           AppointmentService appointmentService,
                           BloodService bloodService,
                           AnswerService answerService){
        this.repository = repository;
        this.appointmentService = appointmentService;
        this.bloodService = bloodService;
        this.answerService = answerService;
    }

    public void createDonation(CreateDonationDTO dto) throws Exception {
        Appointment app = appointmentService.getById(dto.getAppointmentId());
        if(app == null) throw new Exception("Appointment not found.");
        if(!app.isStarted()) throw new Exception("Appointment has not been started yet.");
        if(app.getDonation() != null) throw new Exception("Appointment has already been finished.");
        if(!canUserDonate(app.getUser().getId())) throw new Exception("This user can't donate.");

        Donation donation = new Donation(dto);
        donation.setUser(app.getUser());
        donation.setAppointment(app);

        bloodService.addBlood(donation.getBloodType(), donation.getBloodAmount());
        repository.save(donation);
    }

    private boolean canUserDonate(long userId){
        List<Answer> answers = answerService.getAnswersByUserId(userId);
        for (Answer ans: answers) {
            if(ans.isChecked()) return false;
        }
        return true;
    }
}
