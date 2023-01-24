package bloodcenter.appointment;

import bloodcenter.appointment.dto.AppointmentDTO;
import bloodcenter.appointment.dto.CreateAppointmentDTO;
import bloodcenter.available_appointment.AvailableAppointment;
import bloodcenter.available_appointment.AvailableAppointmentService;
import bloodcenter.email.service.EmailService;
import bloodcenter.exceptions.QuestionnaireNotCompleted;
import bloodcenter.exceptions.UserCannotGiveBloodException;
import bloodcenter.person.model.User;
import bloodcenter.person.service.UserService;
import bloodcenter.questionnaire.service.QuestionnaireService;
import bloodcenter.security.filter.AuthUtility;
import bloodcenter.utils.QRCodeGenerator;
import com.google.zxing.WriterException;
import bloodcenter.email.EmailSender;
import bloodcenter.email.service.EmailService;
import bloodcenter.utils.ObjectsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AppointmentService {
    private final AppointmentRepository repository;
    private final UserService userService;
    private final AvailableAppointmentService availableAppointmentService;
    private final QuestionnaireService questionnaireService;
    private final EmailService emailService;

    private final String QRPath = "src/main/resources/qrcodes/";

    @Autowired
    public AppointmentService(AppointmentRepository repository, UserService userService,
                              AvailableAppointmentService availableAppointmentService,
                              QuestionnaireService questionnaireService, EmailService emailService){
        this.repository = repository;
        this.userService = userService;
        this.availableAppointmentService = availableAppointmentService;
        this.questionnaireService = questionnaireService;
        this.emailService = emailService;
                              
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
    public boolean HaveYouGiveBloodLastSixMonths(Long userId){
        User user = userService.getById(userId).orElse(null);
        LocalDateTime beforeSixMonths = LocalDateTime.now().minusMonths(6);
        List<Appointment>appointments = repository.getAppointmentByEndAfterAndUser(beforeSixMonths, user);

        return appointments.size() != 0;
    }

    public void userCreateAppointment(CreateAppointmentDTO appointmentDTO) throws MessagingException {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        LocalDateTime dateTime = LocalDateTime.parse(appointmentDTO.getSelectedDate(), formatter);
        AvailableAppointment selectedAvailableAppointment = availableAppointmentService.getByUserSelectedDateAndBcId(dateTime,
                appointmentDTO.getBranchCenterId());
        Appointment appointment = new Appointment();
        appointment.setBegin(selectedAvailableAppointment.getStart());
        appointment.setEnd(selectedAvailableAppointment.getEnd());
        User user = userService.getById(appointmentDTO.getUserId()).get();
        appointment.setUser(user);
        repository.save(appointment);
        availableAppointmentService.remove(selectedAvailableAppointment);
        emailService.send(user.getEmail(),"Appointment informations",buildEmail(appointment));
    }

    public List<AppointmentDTO> findAllInFutureByUserId(long userId) {
        List<Appointment> allAppointments = repository.getAllByUser_Id(userId);
        List<AppointmentDTO> appointmentsToReturn = new ArrayList<>();
        LocalDateTime dateNow = LocalDateTime.now();
        for (Appointment appointment:allAppointments) {
            if(appointment.getBegin().isAfter(dateNow))
                appointmentsToReturn.add(ObjectsMapper.convertAppointmentToDTO(appointment));
        }
        return appointmentsToReturn;
    }

    private String buildEmail(Appointment appointment) {
        return "<div style=\"font-family:Helvetica,Arial,sans-serif;font-size:16px;margin:0;color:#0b0c0c\">\n" +
                "\n" +
                "<span style=\"display:none;font-size:1px;color:#fff;max-height:0\"></span>\n" +
                "\n" +
                "  <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse;min-width:100%;width:100%!important\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n" +
                "    <tbody><tr>\n" +
                "      <td width=\"100%\" height=\"53\" bgcolor=\"#0b0c0c\">\n" +
                "        \n" +
                "        <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse;max-width:580px\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" align=\"center\">\n" +
                "          <tbody><tr>\n" +
                "            <td width=\"70\" bgcolor=\"#0b0c0c\" valign=\"middle\">\n" +
                "                <table role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse\">\n" +
                "                  <tbody><tr>\n" +
                "                    <td style=\"padding-left:10px\">\n" +
                "                  \n" +
                "                    </td>\n" +
                "                    <td style=\"font-size:28px;line-height:1.315789474;Margin-top:4px;padding-left:10px\">\n" +
                "                      <span style=\"font-family:Helvetica,Arial,sans-serif;font-weight:700;color:#ffffff;text-decoration:none;vertical-align:top;display:inline-block\">Congratulations :)</span>\n" +
                "                    </td>\n" +
                "                  </tr>\n" +
                "                </tbody></table>\n" +
                "              </a>\n" +
                "            </td>\n" +
                "          </tr>\n" +
                "        </tbody></table>\n" +
                "        \n" +
                "      </td>\n" +
                "    </tr>\n" +
                "  </tbody></table>\n" +
                "  <table role=\"presentation\" class=\"m_-6186904992287805515content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;max-width:580px;width:100%!important\" width=\"100%\">\n" +
                "    <tbody><tr>\n" +
                "      <td width=\"10\" height=\"10\" valign=\"middle\"></td>\n" +
                "      <td>\n" +
                "        \n" +
                "                <table role=\"presentation\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse\">\n" +
                "                  <tbody><tr>\n" +
                "                    <td bgcolor=\"#B81D1D\" width=\"100%\" height=\"10\"></td>\n" +
                "                  </tr>\n" +
                "                </tbody></table>\n" +
                "        \n" +
                "      </td>\n" +
                "      <td width=\"10\" valign=\"middle\" height=\"10\"></td>\n" +
                "    </tr>\n" +
                "  </tbody></table>\n" +
                "\n" +
                "\n" +
                "\n" +
                "  <table role=\"presentation\" class=\"m_-6186904992287805515content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;max-width:580px;width:100%!important\" width=\"100%\">\n" +
                "    <tbody><tr>\n" +
                "      <td height=\"30\"><br></td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "      <td width=\"10\" valign=\"middle\"><br></td>\n" +
                "      <td style=\"font-family:Helvetica,Arial,sans-serif;font-size:19px;line-height:1.315789474;max-width:560px\">\n" +
                "        \n" +
                "            <p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\">Hi " + appointment.getUser().getFirstname() + ",</p><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\"> Thank you for scheduling appointment for blood donation.</p>" +
                "<p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\">Appointment begin at :" + appointment.getBegin() + "</p>" +
                "<p style=\"Margin:0 0 10px 0;font-size:19px;line-height:25px;color:#0b0c0c\">Appointment end at :" + appointment.getEnd() + "</p>" +
        "<blockquote style=\"Margin:0 0 20px 0;border-left:10px solid #b1b4b6;padding:15px 0 0.1px 15px;font-size:19px;line-height:25px\">" +
                "        \n" +
                "      </td>\n" +
                "      <td width=\"10\" valign=\"middle\"><br></td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "      <td height=\"30\"><br></td>\n" +
                "    </tr>\n" +
                "  </tbody></table><div class=\"yj6qo\"></div><div class=\"adL\">\n" +
                "\n" +
                "</div></div>";
    }


    public void scheduleAppointment(HttpServletRequest request, Long id)
            throws UserCannotGiveBloodException, QuestionnaireNotCompleted, IOException, WriterException, MessagingException {
        String userEmail = AuthUtility.getEmailFromRequest(request);
        var user = userService.getUser(userEmail);
        if (HaveYouGiveBloodLastSixMonths(user.getId())) {
            throw new UserCannotGiveBloodException();
        }
        if (questionnaireService.getAnsweredQuestionnaireByUserId(user.getId()) == null) {
            throw new QuestionnaireNotCompleted();
        }

        var availableAppointment = availableAppointmentService.getAvailableAppointmentById(id);
        var appointment = new Appointment();
        appointment.setTitle(availableAppointment.getTitle());
        appointment.setBegin(availableAppointment.getStart());
        appointment.setStarted(false);
        appointment.setEnd(availableAppointment.getEnd());
        appointment.setUser(user);

        availableAppointmentService.remove(availableAppointment);
        repository.save(appointment);

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy.");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

        String QRCodeText =
                "Appointment: " + appointment.getTitle() + "\n" +
                "User: " + appointment.getUser().getFirstname() + " " + appointment.getUser().getLastname() + "\n" +
                "Time: " + appointment.getBegin().format(dateFormatter) + " " +
                appointment.getBegin().format(timeFormatter) + " - " +
                appointment.getEnd().format(timeFormatter) + "\n";

        String QRCodeCreatedPath = QRPath + appointment.getUser().getEmail() + ".png";
        QRCodeGenerator.generateQRCodeImage(QRCodeText, 250, 250, QRCodeCreatedPath);

        String qrPath = FileSystems.getDefault().getPath(QRCodeCreatedPath).toString();
        emailService.sendWithImage(appointment.getUser().getEmail(), "Appointment scheduled",
                buildAppointmentEmail(appointment.getUser().getFirstname()), qrPath);
    }

    private String buildAppointmentEmail(String name) {
        return "<div style=\"font-family:Helvetica,Arial,sans-serif;font-size:16px;margin:0;color:#0b0c0c\">\n" +
                "\n" +
                "<span style=\"display:none;font-size:1px;color:#fff;max-height:0\"></span>\n" +
                "\n" +
                "  <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse;min-width:100%;width:100%!important\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n" +
                "    <tbody><tr>\n" +
                "      <td width=\"100%\" height=\"53\" bgcolor=\"#0b0c0c\">\n" +
                "        \n" +
                "        <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse;max-width:580px\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" align=\"center\">\n" +
                "          <tbody><tr>\n" +
                "            <td width=\"70\" bgcolor=\"#0b0c0c\" valign=\"middle\">\n" +
                "                <table role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse\">\n" +
                "                  <tbody><tr>\n" +
                "                    <td style=\"padding-left:10px\">\n" +
                "                  \n" +
                "                    </td>\n" +
                "                    <td style=\"font-size:28px;line-height:1.315789474;Margin-top:4px;padding-left:10px\">\n" +
                "                      <span style=\"font-family:Helvetica,Arial,sans-serif;font-weight:700;color:#ffffff;text-decoration:none;vertical-align:top;display:inline-block\">Appointment scheduled</span>\n" +
                "                    </td>\n" +
                "                  </tr>\n" +
                "                </tbody></table>\n" +
                "              </a>\n" +
                "            </td>\n" +
                "          </tr>\n" +
                "        </tbody></table>\n" +
                "        \n" +
                "      </td>\n" +
                "    </tr>\n" +
                "  </tbody></table>\n" +
                "  <table role=\"presentation\" class=\"m_-6186904992287805515content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;max-width:580px;width:100%!important\" width=\"100%\">\n" +
                "    <tbody><tr>\n" +
                "      <td width=\"10\" height=\"10\" valign=\"middle\"></td>\n" +
                "      <td>\n" +
                "        \n" +
                "                <table role=\"presentation\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse\">\n" +
                "                  <tbody><tr>\n" +
                "                    <td bgcolor=\"#B81D1D\" width=\"100%\" height=\"10\"></td>\n" +
                "                  </tr>\n" +
                "                </tbody></table>\n" +
                "        \n" +
                "      </td>\n" +
                "      <td width=\"10\" valign=\"middle\" height=\"10\"></td>\n" +
                "    </tr>\n" +
                "  </tbody></table>\n" +
                "\n" +
                "\n" +
                "\n" +
                "  <table role=\"presentation\" class=\"m_-6186904992287805515content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;max-width:580px;width:100%!important\" width=\"100%\">\n" +
                "    <tbody><tr>\n" +
                "      <td height=\"30\"><br></td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "      <td width=\"10\" valign=\"middle\"><br></td>\n" +
                "      <td style=\"font-family:Helvetica,Arial,sans-serif;font-size:19px;line-height:1.315789474;max-width:560px\">\n" +
                "        \n" +
                "            <p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\">Hi " + name + ",</p><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\">Your appointment has been scheduled.</p><br>You can use this QR code to view details of your appointment, including the date and time and any necessary instructions." +
                "        \n" +
                "      </td>\n" +
                "      <td width=\"10\" valign=\"middle\"><br></td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "      <td height=\"30\"><br></td>\n" +
                "    </tr>\n" +
                "  </tbody></table><div class=\"yj6qo\"></div><div class=\"adL\">\n" +
                "\n" +
                "</div></div>";
    }
}
