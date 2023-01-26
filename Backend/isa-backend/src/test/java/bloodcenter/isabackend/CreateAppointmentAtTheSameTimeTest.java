package bloodcenter.isabackend;

import bloodcenter.appointment.AppointmentService;
import bloodcenter.available_appointment.AvailableAppointment;
import bloodcenter.available_appointment.AvailableAppointmentService;
import bloodcenter.exceptions.AppointmentNotAvailableAnymore;
import bloodcenter.exceptions.QuestionnaireNotCompleted;
import bloodcenter.exceptions.UserCannotGiveBloodException;
import bloodcenter.exceptions.UserPenaltiesException;
import bloodcenter.person.enums.Sex;
import bloodcenter.person.model.User;
import bloodcenter.person.model.UserRank;
import bloodcenter.person.service.UserService;
import bloodcenter.questionnaire.enums.QuestionType;
import bloodcenter.questionnaire.model.Answer;
import bloodcenter.questionnaire.model.Question;
import bloodcenter.questionnaire.service.AnswerService;
import bloodcenter.questionnaire.service.QuestionService;
import com.google.zxing.WriterException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.test.context.junit4.SpringRunner;

import javax.mail.MessagingException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CreateAppointmentAtTheSameTimeTest {

    @Autowired
    private AppointmentService appointmentService;
    @Autowired
    private AvailableAppointmentService availableAppointmentService;
    @Autowired
    private UserService userService;
    @Autowired
    private AnswerService answerService;
    @Autowired
    private QuestionService questionService;

    @Before
    public void setUp() throws Exception {
        AvailableAppointment availableAppointment = new AvailableAppointment(1L,"Slobodan test", LocalDateTime.of(2023, Month.FEBRUARY, 21, 15, 0),LocalDateTime.of(2023, Month.FEBRUARY, 21, 16, 0));
        availableAppointmentService.save(availableAppointment);
        User u1 = new User("Test", "Test", "test@gmail.com", "$2a$10$2WkfD1m/Ff5ZsB7JClTLfemMsAWzzaGPXoYFKlMY725YHcApCG8Je","0641232133","1234567491011", Sex.MALE,"Default occupation","Default information", UserRank.Regular, 500);
        User u2 = new User("Test2", "Test2", "test2@gmail.com", "$2a$10$2WkfD1m/Ff5ZsB7JClTLfemMsAWzzaGPXoYFKlMY725YHcApCG8Je","0611242133","1134467491011", Sex.MALE,"Default occupation","Default information", UserRank.Regular, 500);
        userService.saveUser(u1);
        userService.saveUser(u2);
        Question question = new Question("Test question?", QuestionType.FOR_EVERYONE);
        Question question2 = new Question("Test question2?", QuestionType.FOR_EVERYONE);
        questionService.save(question);
        questionService.save(question2);
        Answer answer = new Answer(question, u1, true);
        Answer answer2 = new Answer(question2, u2, true);
        answerService.save(answer);
        answerService.save(answer2);
    }

    @Test(expected = ObjectOptimisticLockingFailureException.class)
    public void testOptimisticMultipleSchedule() throws Throwable {
        ExecutorService executor = Executors.newFixedThreadPool(2);
        Future<?> future1 = executor.submit(new Runnable() {

            @Override
            public void run() {
                System.out.println("Startovan thread 1");
                AvailableAppointment appointmentToRespond = availableAppointmentService.findById(1l);
                try { Thread.sleep(3000); } catch (InterruptedException ignored) {}
                try {
                    appointmentService.scheduleAppointment("test@gmail.com", 1L);
                } catch (QuestionnaireNotCompleted | IOException | WriterException | MessagingException |
                         AppointmentNotAvailableAnymore | UserCannotGiveBloodException | UserPenaltiesException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        executor.submit(new Runnable() {
            @Override
            public void run() {
                System.out.println("Startovan thread 2");
                AvailableAppointment appointmentToRespond = availableAppointmentService.findById(1L);
                try {
                    appointmentService.scheduleAppointment("test2@gmail.com", 1L);
                } catch (QuestionnaireNotCompleted | IOException | WriterException | MessagingException |
                         AppointmentNotAvailableAnymore | UserCannotGiveBloodException | UserPenaltiesException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        try {
            future1.get();
        } catch (ExecutionException e) {
            System.out.println("Exception from thread " + e.getCause().getClass());
            throw e.getCause();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        executor.shutdown();
    }
}
