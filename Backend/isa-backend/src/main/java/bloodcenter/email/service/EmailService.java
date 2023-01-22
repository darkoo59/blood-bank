package bloodcenter.email.service;

import bloodcenter.email.EmailSender;
import lombok.AllArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;


@Service
@AllArgsConstructor
public class EmailService implements EmailSender {

    private final JavaMailSender mailSender;

    @Override
    @Async
    public void send(String to, String subject, String text) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
        helper.setText(text, true);
        helper.setSubject(subject);
        helper.setTo(to);
        helper.setFrom("isabloodbank@gmail.com");
        mailSender.send(mimeMessage);
    }

    @Async
    public void sendWithImage(String to, String subject, String text, String imagePath) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
        MimeBodyPart messageBodyPart1 = new MimeBodyPart();
        messageBodyPart1.setContent(text, "text/html");
        MimeBodyPart messageBodyPart2 = new MimeBodyPart();
        DataSource source = new FileDataSource(imagePath);
        messageBodyPart2.setDataHandler(new DataHandler(source));
        messageBodyPart2.setHeader("Content-ID", "<image>");
        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(messageBodyPart1);
        multipart.addBodyPart(messageBodyPart2);
        mimeMessage.setContent(multipart);
        helper.setSubject(subject);
        helper.setTo(to);
        helper.setFrom("isabloodbank@gmail.com");
        mailSender.send(mimeMessage);
    }
}
