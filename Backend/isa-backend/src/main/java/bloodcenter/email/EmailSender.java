package bloodcenter.email;

import javax.mail.MessagingException;

public interface EmailSender {
    void send(String to, String subject, String email) throws MessagingException;
}
