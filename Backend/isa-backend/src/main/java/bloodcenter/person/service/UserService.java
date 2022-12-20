package bloodcenter.person.service;

import bloodcenter.address.AddressRepository;
import bloodcenter.email.EmailSender;
import bloodcenter.person.model.Person;
import bloodcenter.registration.failed_confirmation_mails.service.FailedConfirmationMailService;
import bloodcenter.registration.token.model.ConfirmationToken;
import bloodcenter.registration.token.service.ConfirmationTokenService;
import bloodcenter.role.Role;
import bloodcenter.role.RoleRepository;
import bloodcenter.person.dto.RegisterDTO;
import bloodcenter.person.model.User;
import bloodcenter.person.repository.UserRepository;
import bloodcenter.utils.ObjectsMapper;
import exceptions.EmailExistsException;
import exceptions.TokenExpiredException;
import exceptions.TokenNotFoundException;
import exceptions.UserConfirmedException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.retry.RetryContext;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final AddressRepository addressRepository;
    private final PasswordEncoder passwordEncoder;
    private final ConfirmationTokenService confirmationTokenService;
    private final EmailSender emailSender;
    private final FailedConfirmationMailService failedConfirmationMailService;

    @Value("${server.port}")
    private int port;

    public void saveUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    public void addRoleToUser(String email, String roleName) {
        User user = userRepository.findByEmail(email).get();
        Role role = roleRepository.findByName(roleName);
        user.getRoles().add(role);
    }

    public User getUser(String email) {
        return userRepository.findByEmail(email).isPresent() ?
                userRepository.findByEmail(email).get() : null;
    }

    public List<User> getAll() { return this.userRepository.findAll(); }

    public void registerUser(RegisterDTO registerDTO) throws EmailExistsException, MessagingException {
        User user = ObjectsMapper.convertRegisterDTOToUser(registerDTO);
        boolean emailExists = userRepository.findByEmail(user.getEmail()).isPresent();
        if (emailExists) {
            throw new EmailExistsException();
        }
        Role role = roleRepository.findByName("ROLE_USER");
        if (role == null) {
             role = new Role(0, "ROLE_USER");
             roleRepository.save(role);
        }
        addressRepository.save(user.getAddress());
        saveUser(user);
        addRoleToUser(user.getEmail(), role.getName());

        String token = UUID.randomUUID().toString();
        ConfirmationToken confirmationToken = new ConfirmationToken(
                token,
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(30),
                user
        );
        confirmationTokenService.saveConfirmationToken(confirmationToken);
        String link = "http://localhost:" + port + "/registration/confirm?token=" + token;

        SimpleRetryPolicy retryPolicy = new SimpleRetryPolicy(
                3, Collections.singletonMap(MessagingException.class, true));
        RetryTemplate retryTemplate = new RetryTemplate();
        retryTemplate.setRetryPolicy(retryPolicy);

        try {
            retryTemplate.execute((RetryContext retryContext) -> {
                emailSender.send(user.getEmail(), "Confirm your email", buildEmail(user.getFirstname(), link));
                return null;
            });
        } catch (MessagingException e) {
            failedConfirmationMailService.saveFailedConfirmationMails(user);
            throw e;
        }
    }

    private String buildEmail(String name, String link) {
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
                "                      <span style=\"font-family:Helvetica,Arial,sans-serif;font-weight:700;color:#ffffff;text-decoration:none;vertical-align:top;display:inline-block\">Confirm your email</span>\n" +
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
                "            <p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\">Hi " + name + ",</p><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\"> Thank you for registering. Please click on the link below to activate your account: </p><blockquote style=\"Margin:0 0 20px 0;border-left:10px solid #b1b4b6;padding:15px 0 0.1px 15px;font-size:19px;line-height:25px\"><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\"> <a href=\"" + link + "\">Activate Now</a> </p></blockquote>\n Link will expire in 30 minutes." +
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

    @Transactional
    public boolean confirmToken(String token) throws
            TokenNotFoundException, UserConfirmedException, TokenExpiredException {

        ConfirmationToken confirmationToken = confirmationTokenService.getToken(token)
                .orElseThrow(TokenNotFoundException::new);
        if (confirmationToken.getConfirmedAt() != null) {
            throw new UserConfirmedException();
        }

        LocalDateTime expiredAt = confirmationToken.getExpiresAt();

        if (expiredAt.isBefore((LocalDateTime.now()))) {
            throw new TokenExpiredException();
        }

        confirmationTokenService.setConfirmedAt(token);
        return userRepository.enableAppUser(confirmationToken.getUser().getEmail()) != 0;
    }

    public int enableUser(String email) {
        return userRepository.enableAppUser(email);
    }

    public List<User> searchUsers(String searchInput) {
        return userRepository.searchUsers(searchInput.toLowerCase());
    }

    public void update(Person personToUpdate) {
        User userToUpdate = getUser(personToUpdate.getEmail());
        userToUpdate.setPassword(personToUpdate.getPassword());
        userToUpdate.setAddress(personToUpdate.getAddress());
        userToUpdate.setEmail(personToUpdate.getEmail());
        userToUpdate.setFirstname(personToUpdate.getFirstname());
        userToUpdate.setInformation(personToUpdate.getInformation());
        userToUpdate.setLastname(personToUpdate.getLastname());
        userToUpdate.setNationalId(personToUpdate.getNationalId());
        userToUpdate.setOccupation(personToUpdate.getOccupation());
        userToUpdate.setPhone(personToUpdate.getPhone());
        userToUpdate.setSex(personToUpdate.getSex());
        userRepository.save(userToUpdate);
    }
}
