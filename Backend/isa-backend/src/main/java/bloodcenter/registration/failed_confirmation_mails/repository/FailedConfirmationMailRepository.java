package bloodcenter.registration.failed_confirmation_mails.repository;

import bloodcenter.registration.failed_confirmation_mails.model.FailedConfirmationMail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FailedConfirmationMailRepository extends JpaRepository<FailedConfirmationMail, Long> {
}
