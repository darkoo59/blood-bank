package bloodcenter.registration.failed_confirmation_mails.service;

import bloodcenter.person.model.User;
import bloodcenter.registration.failed_confirmation_mails.model.FailedConfirmationMail;
import bloodcenter.registration.failed_confirmation_mails.repository.FailedConfirmationMailRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class FailedConfirmationMailService {
    private final FailedConfirmationMailRepository failedConfirmationMailRepository;

    public void saveFailedConfirmationMails(User recipient) {
        FailedConfirmationMail mail = new FailedConfirmationMail(recipient, LocalDateTime.now());
        failedConfirmationMailRepository.save(mail);
    }
}
