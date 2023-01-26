package bloodcenter;

import bloodcenter.address.AddressRepository;
import bloodcenter.appointment.Appointment;
import bloodcenter.appointment.AppointmentRepository;
import bloodcenter.available_appointment.AvailableAppointment;
import bloodcenter.available_appointment.AvailableAppointmentRepository;
import bloodcenter.blood.BloodType;
import bloodcenter.blood.Blood;
import bloodcenter.api_key.Key;
import bloodcenter.branch_center.BranchCenter;
import bloodcenter.branch_center.BranchCenterRepository;
import bloodcenter.address.Address;
import bloodcenter.complaint.Complaint;
import bloodcenter.complaint.ComplaintRepository;
import bloodcenter.feedback.Feedback;
import bloodcenter.feedback.FeedbackRepository;
import bloodcenter.person.enums.Sex;
import bloodcenter.person.model.Admin;
import bloodcenter.person.model.UserRank;
import bloodcenter.person.repository.AdminRepository;
import bloodcenter.questionnaire.enums.QuestionType;
import bloodcenter.questionnaire.model.Question;
import bloodcenter.questionnaire.model.Questionnaire;
import bloodcenter.questionnaire.repository.QuestionRepository;
import bloodcenter.questionnaire.repository.QuestionnaireRepository;
import bloodcenter.role.Role;
import bloodcenter.role.RoleRepository;
import bloodcenter.person.model.BCAdmin;
import bloodcenter.blood.BloodRepository;
import bloodcenter.api_key.KeyRepository;
import bloodcenter.person.model.User;
import bloodcenter.person.repository.BCAdminRepository;
import bloodcenter.person.repository.UserRepository;
import bloodcenter.working_days.WorkingDay;
import bloodcenter.working_days.WorkingDaysRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.List;

@Configuration
@EnableScheduling
public class BloodConfiguration {
    @Bean
    CommandLineRunner BloodCLR(BloodRepository repository) {
        return args -> {
            Blood blood1 = new Blood(BloodType.ABPositive, 32.5f);
            Blood blood2 = new Blood(BloodType.ANegative, 5.0f);
            Blood blood3 = new Blood(BloodType.ONegative, 50.2f);
            Blood blood4 = new Blood(BloodType.BPositive, 17.3f);
            Blood blood5 = new Blood(BloodType.ABNegative, 12.5f);
            Blood blood6 = new Blood(BloodType.APositive, 2.0f);
            Blood blood7 = new Blood(BloodType.OPositive, 40.2f);
            Blood blood8 = new Blood(BloodType.BNegative, 52.3f);

            repository.saveAll(List.of(blood1, blood2, blood3, blood4, blood5, blood6, blood7, blood8));
        };
    }

    @Bean
    CommandLineRunner keyCLR(KeyRepository repository) {
        return args -> {
            Key key1 = new Key("email1@gmail.com", "kljuc_1");
            Key key2 = new Key("email2@gmail.com", "kljuc_2");
            Key key3 = new Key("email3@gmail.com", "kljuc_3");

            repository.saveAll(List.of(key1, key2, key3));
        };
    }

    @Bean
    CommandLineRunner RoleCLR(RoleRepository repository) {
        return args -> {
            Role role1 = new Role("ROLE_ADMIN");
            Role role2 = new Role("ROLE_BCADMIN");
            Role role3 = new Role("ROLE_USER");

            repository.saveAll(List.of(role1, role2, role3));
        };
    }

    @Bean
    CommandLineRunner AddressCLR(AddressRepository repository) {
        return args -> {
            Address ad1 = new Address(45.259565, 19.824143, "Pariske komune", "2", "Novi Sad", "Serbia");
            Address ad2 = new Address(44.818768, 20.459739, "Brace Jugovica", "12", "Beograd", "Serbia");
            Address ad3 = new Address(48.815469, 2.317124, "Avenue Verdier", "44", "Paris", "France");
            Address ad4 = new Address(51.344494, 12.369030, "Jacobstrabe", "13", "Leipzig", "Germany");
            Address ad5 = new Address(48.203554, 16.350535, "Stuckgasse", "26", "Wien", "Austria");
            repository.saveAll(List.of(ad1, ad2, ad3,ad4,ad5));
        };
    }

    @Bean
    CommandLineRunner WorkingDaysCLR(WorkingDaysRepository repository) {
        return args -> {
            WorkingDay wd1 = new WorkingDay(true,true,true,true,true,false,false);
            WorkingDay wd2 = new WorkingDay(true,true,true,true,true,true,true);
            WorkingDay wd3 = new WorkingDay(true,true,true,true,true,true,false);
            repository.saveAll(List.of(wd1, wd2, wd3));
        };
    }

    @Bean
    CommandLineRunner BranchCenterCLR(BranchCenterRepository repository, AddressRepository address_repo, WorkingDaysRepository workingDaysRepository) {
        return args -> {
            BranchCenter c1 = new BranchCenter("Happy", "Najbolji novosadski centar", LocalTime.of(8, 0),LocalTime.of(21,0));
            BranchCenter c2 = new BranchCenter("Lab", "Dobrovoljno davanje krvi - Beograd", LocalTime.of(8, 0),LocalTime.of(21,0));
            BranchCenter c3 = new BranchCenter("Euro lab", "Don de sang volontaire - Paris", LocalTime.of(8, 0),LocalTime.of(21,0));
            BranchCenter c4 = new BranchCenter("Jacobs lab", "Freiwillige Blutspende Deutschland", LocalTime.of(8, 0),LocalTime.of(21,0));
            BranchCenter c5 = new BranchCenter("Blood - Wien", "Freiwillige Blutspende Österreich", LocalTime.of(8, 0),LocalTime.of(21,0));

            c1.setAddress(address_repo.findById(1L).get());
            c2.setAddress(address_repo.findById(2L).get());
            c3.setAddress(address_repo.findById(3L).get());
            c4.setAddress(address_repo.findById(4L).get());
            c5.setAddress(address_repo.findById(5L).get());

            c1.setWorkingDays(workingDaysRepository.findById(3L).get());
            c2.setWorkingDays(workingDaysRepository.findById(3L).get());
            c3.setWorkingDays(workingDaysRepository.findById(3L).get());
            c4.setWorkingDays(workingDaysRepository.findById(3L).get());
            c5.setWorkingDays(workingDaysRepository.findById(3L).get());


            repository.saveAll(List.of(c1, c2, c3, c4, c5));
        };
    }

    @Bean
    CommandLineRunner BCAdminCLR(BCAdminRepository repository, BranchCenterRepository bc_repo, RoleRepository role_repo) {
        return args -> {
            BCAdmin a1 = new BCAdmin("Rade", "Stojanovic", "rade123@gmail.com", "$2a$10$2WkfD1m/Ff5ZsB7JClTLfemMsAWzzaGPXoYFKlMY725YHcApCG8Je","0641232133","1234567891011", Sex.MALE,"Default occupation","Default information");
            BCAdmin a2 = new BCAdmin("Darko", "Selakovic", "darko123@gmail.com", "$2a$10$2WkfD1m/Ff5ZsB7JClTLfemMsAWzzaGPXoYFKlMY725YHcApCG8Je","064143421","1234567391011",Sex.MALE,"Default occupation","Default information");
            BCAdmin a3 = new BCAdmin("Vojin", "Bjelica", "vojin123@gmail.com", "$2a$10$2WkfD1m/Ff5ZsB7JClTLfemMsAWzzaGPXoYFKlMY725YHcApCG8Je","0646677732","1264567891221",Sex.MALE,"Default occupation","Default information");
            BCAdmin a4 = new BCAdmin("Marko", "Uljarevic", "marko123@gmail.com", "$2a$10$2WkfD1m/Ff5ZsB7JClTLfemMsAWzzaGPXoYFKlMY725YHcApCG8Je","064312221","1324567691011",Sex.MALE,"Default occupation","Default information");

            a1.setBranchCenter(bc_repo.findById(1L).get());
            a2.setBranchCenter(bc_repo.findById(1L).get());
            a3.setBranchCenter(bc_repo.findById(2L).get());

            Role role = role_repo.findByName("ROLE_BCADMIN");
            a1.getRoles().add(role);
            a2.getRoles().add(role);
            a3.getRoles().add(role);
            a4.getRoles().add(role);

            a1.setEnabled(true);
            a2.setEnabled(true);
            a3.setEnabled(true);
            a4.setEnabled(true);

            repository.saveAll(List.of(a1, a2, a3, a4));
        };
    }

    @Bean
    CommandLineRunner AdminCLR(AdminRepository repository, RoleRepository role_repo) {
        return args -> {
            Admin a1 = new Admin("Elon", "Musk", "elonmusk@tesla.com", "$2a$10$2WkfD1m/Ff5ZsB7JClTLfemMsAWzzaGPXoYFKlMY725YHcApCG8Je","0641233153","7234567891011", Sex.MALE,"Default occupation","Default information", true);
            Role role = role_repo.findByName("ROLE_ADMIN");
            a1.getRoles().add(role);
            a1.setEnabled(true);
            repository.saveAll(List.of(a1));
        };
    }

    @Bean
    CommandLineRunner UserCLR(UserRepository repository, RoleRepository role_repo) {
        return args -> {
            User u1 = new User("Rade", "Stojanovic", "rade@gmail.com", "$2a$10$2WkfD1m/Ff5ZsB7JClTLfemMsAWzzaGPXoYFKlMY725YHcApCG8Je","0641232133","1234567891011", Sex.MALE,"Default occupation","Default information", UserRank.Regular, 500);
            User u3 = new User("Vojin", "Bjelica", "vojinb111@gmail.com", "$2a$10$2WkfD1m/Ff5ZsB7JClTLfemMsAWzzaGPXoYFKlMY725YHcApCG8Je","0646677732","1264567891221",Sex.MALE,"Default occupation","Default information",UserRank.Silver, 1500);
            User u2 = new User("Darko", "Selakovic", "darko.selakovic11@gmail.com", "$2a$10$2WkfD1m/Ff5ZsB7JClTLfemMsAWzzaGPXoYFKlMY725YHcApCG8Je","064143421","1234567391011",Sex.MALE,"Default occupation","Default information",UserRank.Gold, 2500);
            User u4 = new User("Marko", "Uljarevic", "marko@gmail.com", "$2a$10$2WkfD1m/Ff5ZsB7JClTLfemMsAWzzaGPXoYFKlMY725YHcApCG8Je","064312221","1324567691011",Sex.MALE,"Default occupation","Default information",UserRank.Regular,0);

            u1.setEnabled(true);
            u2.setEnabled(true);
            u3.setEnabled(true);
            u4.setEnabled(true);

            Role role = role_repo.findByName("ROLE_USER");
            u1.getRoles().add(role);
            u2.getRoles().add(role);
            u3.getRoles().add(role);
            u4.getRoles().add(role);

            repository.saveAll(List.of(u1, u2, u3, u4));
        };
    }

    @Bean
    CommandLineRunner AppointmentCLR(AppointmentRepository repository, UserRepository user_repo){
        return args -> {
            User u = user_repo.findByEmail("rade@gmail.com").get();
            User u2 = user_repo.findByEmail("marko@gmail.com").get();
            Appointment a1 = new Appointment(u);
            Appointment a2 = new Appointment(u);
            Appointment a3 = new Appointment(u);
            Appointment a4 = new Appointment(u2);
            Appointment a5 = new Appointment(u2);

            a1.setBegin(LocalDateTime.of(2023, Month.JANUARY, 21, 19, 0));
            a1.setEnd(LocalDateTime.of(2023, Month.JANUARY, 21, 20, 30));
            a1.setTitle("Naslov 1");
            a2.setBegin(LocalDateTime.of(2023, Month.JANUARY, 22, 12, 30));
            a2.setEnd(LocalDateTime.of(2023, Month.JANUARY, 22, 13, 0));
            a2.setTitle("Naslov 2");
            a3.setBegin(LocalDateTime.of(2023, Month.JANUARY, 25, 12, 30));
            a3.setEnd(LocalDateTime.of(2023, Month.JANUARY, 25, 13, 0));
            a3.setTitle("Naslov 3");
            a4.setTitle("Marko 1");
            a4.setBegin(LocalDateTime.of(2023, Month.JANUARY, 26, 12, 30));
            a4.setEnd(LocalDateTime.of(2023, Month.JANUARY, 26, 15, 0));
            a5.setTitle("Marko 2");
            a5.setBegin(LocalDateTime.of(2023, Month.JANUARY, 26, 16, 0));
            a5.setEnd(LocalDateTime.of(2023, Month.JANUARY, 26, 17, 0));

            repository.saveAll(List.of(a1, a2, a3, a4, a5));
        };
    }

    @Bean
    CommandLineRunner AvailableAppointmentCLR(AvailableAppointmentRepository repository, BranchCenterRepository bc_repo){
        return args -> {
            AvailableAppointment a1 = new AvailableAppointment("Available appointment",LocalDateTime.of(2023, Month.DECEMBER, 21, 18, 00),
                    LocalDateTime.of(2023, Month.DECEMBER, 21, 19, 00));
            AvailableAppointment a2 = new AvailableAppointment("Available appointment",LocalDateTime.of(2023, Month.DECEMBER, 22, 15, 00),
                    LocalDateTime.of(2023, Month.DECEMBER, 22, 15, 30));
            AvailableAppointment a3 = new AvailableAppointment("Available appointment",LocalDateTime.of(2023, Month.DECEMBER, 24, 14, 20),
                    LocalDateTime.of(2023, Month.DECEMBER, 24, 15, 00));
            AvailableAppointment a4 = new AvailableAppointment("Available appointment",LocalDateTime.of(2023, Month.DECEMBER, 24, 14, 20),
                    LocalDateTime.of(2023, Month.DECEMBER, 24, 15, 00));
            AvailableAppointment a5 = new AvailableAppointment("Available appointment",LocalDateTime.of(2023, Month.DECEMBER, 24, 14, 20),
                    LocalDateTime.of(2023, Month.DECEMBER, 24, 15, 00));

            a1.setBranchCenter(bc_repo.findById(1L).get());
            a2.setBranchCenter(bc_repo.findById(1L).get());
            a3.setBranchCenter(bc_repo.findById(1L).get());
            a4.setBranchCenter(bc_repo.findById(2L).get());
            a5.setBranchCenter(bc_repo.findById(3L).get());

            repository.saveAll(List.of(a1, a2, a3, a4, a5));
        };
    }

    @Bean
    CommandLineRunner FeedbackCLR(FeedbackRepository repository, UserRepository user_repo, BranchCenterRepository bc_repo){
        return args -> {
            Feedback f1 = new Feedback("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.", LocalDateTime.now(), 5);
            Feedback f2 = new Feedback("Sit amet commodo nulla facilisi nullam. Bibendum arcu vitae elementum curabitur vitae nunc sed velit dignissim. Amet massa vitae tortor condimentum lacinia quis vel eros donec.", LocalDateTime.now(), 3);
            Feedback f3 = new Feedback("Adipiscing tristique risus nec feugiat in fermentum posuere urna nec.", LocalDateTime.now(), 3);
            Feedback f4 = new Feedback(" Ultrices in iaculis nunc sed. Convallis tellus id interdum velit laoreet id donec ultrices. Egestas sed tempus urna et pharetra pharetra.", LocalDateTime.now(), 3);

            Feedback f5 = new Feedback("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.", LocalDateTime.now(), 3);
            Feedback f6 = new Feedback("Sit amet commodo nulla facilisi nullam. Bibendum arcu vitae elementum curabitur vitae nunc sed velit dignissim. Amet massa vitae tortor condimentum lacinia quis vel eros donec.", LocalDateTime.now(), 5);
            Feedback f7 = new Feedback("Adipiscing tristique risus nec feugiat in fermentum posuere urna nec.", LocalDateTime.now(), 4);
            Feedback f8 = new Feedback(" Ultrices in iaculis nunc sed. Convallis tellus id interdum velit laoreet id donec ultrices. Egestas sed tempus urna et pharetra pharetra.", LocalDateTime.now(), 2);

            Feedback f9 = new Feedback("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.", LocalDateTime.now(), 1);
            Feedback f10 = new Feedback("Sit amet commodo nulla facilisi nullam. Bibendum arcu vitae elementum curabitur vitae nunc sed velit dignissim. Amet massa vitae tortor condimentum lacinia quis vel eros donec.", LocalDateTime.now(), 5);
            Feedback f11 = new Feedback("Adipiscing tristique risus nec feugiat in fermentum posuere urna nec.", LocalDateTime.now(), 1);
            Feedback f12 = new Feedback(" Ultrices in iaculis nunc sed. Convallis tellus id interdum velit laoreet id donec ultrices. Egestas sed tempus urna et pharetra pharetra.", LocalDateTime.now(), 2);

            f1.setUser(user_repo.findByEmail("vojinb111@gmail.com").get());
            f2.setUser(user_repo.findByEmail("rade@gmail.com").get());
            f3.setUser(user_repo.findByEmail("vojinb111@gmail.com").get());
            f4.setUser(user_repo.findByEmail("darko.selakovic11@gmail.com").get());
            f5.setUser(user_repo.findByEmail("marko@gmail.com").get());
            f6.setUser(user_repo.findByEmail("rade@gmail.com").get());
            f7.setUser(user_repo.findByEmail("marko@gmail.com").get());
            f8.setUser(user_repo.findByEmail("darko.selakovic11@gmail.com").get());

            f9.setUser(user_repo.findByEmail("marko@gmail.com").get());
            f10.setUser(user_repo.findByEmail("rade@gmail.com").get());
            f11.setUser(user_repo.findByEmail("marko@gmail.com").get());
            f12.setUser(user_repo.findByEmail("darko.selakovic11@gmail.com").get());

            f1.setBranchCenter(bc_repo.findById(1L).get());
            f2.setBranchCenter(bc_repo.findById(1L).get());
            f3.setBranchCenter(bc_repo.findById(1L).get());
            f4.setBranchCenter(bc_repo.findById(1L).get());
            f5.setBranchCenter(bc_repo.findById(1L).get());
            f6.setBranchCenter(bc_repo.findById(1L).get());
            f7.setBranchCenter(bc_repo.findById(1L).get());
            f8.setBranchCenter(bc_repo.findById(1L).get());
            f9.setBranchCenter(bc_repo.findById(2L).get());
            f10.setBranchCenter(bc_repo.findById(2L).get());
            f11.setBranchCenter(bc_repo.findById(2L).get());
            f12.setBranchCenter(bc_repo.findById(2L).get());

            repository.saveAll(List.of(f1, f2, f3, f4, f5, f6, f7, f8, f9, f10, f11, f12));
        };
    }

    @Bean
    CommandLineRunner QuestionnaireCLR(QuestionnaireRepository repository, QuestionRepository question_repo) {
        return args -> {
            Questionnaire questionnaire = new Questionnaire();
            questionnaire.setName("basic");
            repository.saveAll(List.of(questionnaire));

            Question q1 = new Question("Do you weigh less than 50 kg?", QuestionType.FOR_EVERYONE);
            Question q2 = new Question("Whether you have symptoms of a cold, some illness or simply do not feel well?", QuestionType.FOR_EVERYONE);
            Question q3 = new Question("Do you have skin changes (infections, rashes, fungal diseases...)?", QuestionType.FOR_EVERYONE);
            Question q4 = new Question("Whether your blood pressure is too high or too low", QuestionType.FOR_EVERYONE);
            Question q5 = new Question("Whether you are on therapy or it has not been at least 7 days since antibiotic therapy", QuestionType.FOR_EVERYONE);
            Question q6 = new Question("Whether you are in the phase of a regular menstrual cycle", QuestionType.FOR_FEMALE);
            Question q7 = new Question("Has it not been at least 7 days since tooth extraction or minor dental intervention", QuestionType.FOR_EVERYONE);
            Question q8 = new Question("Has it not been 6 months since body and skin piercing, tattoos or certain surgical interventions and blood transfusions", QuestionType.FOR_EVERYONE);

            q1.setQuestionnaire(questionnaire);
            q2.setQuestionnaire(questionnaire);
            q3.setQuestionnaire(questionnaire);
            q4.setQuestionnaire(questionnaire);
            q5.setQuestionnaire(questionnaire);
            q6.setQuestionnaire(questionnaire);
            q7.setQuestionnaire(questionnaire);
            q8.setQuestionnaire(questionnaire);

            question_repo.saveAll(List.of(q1, q2, q3, q4, q5, q6, q7, q8));
        };
    }

    @Bean
    CommandLineRunner ComplaintCLR(ComplaintRepository repository, UserRepository user_repo, BranchCenterRepository bc_repo) {
        return args -> {
          Complaint c1 = new Complaint("Very bad service.");
          Complaint c2 = new Complaint("Extremely rude behavior.");
          Complaint c3 = new Complaint("I waited for 3 hours for no reason.");
          Complaint c4 = new Complaint("The bathroom had no toilet paper.");

          c1.setUser(user_repo.findByEmail("vojinb111@gmail.com").get());
          c2.setUser(user_repo.findByEmail("rade@gmail.com").get());
          c3.setUser(user_repo.findByEmail("vojinb111@gmail.com").get());
          c4.setUser(user_repo.findByEmail("darko.selakovic11@gmail.com").get());

          c1.setBranchCenter(bc_repo.findById(1L).get());
          c2.setBranchCenter(bc_repo.findById(1L).get());
          c3.setBranchCenter(bc_repo.findById(2L).get());
          c4.setBranchCenter(bc_repo.findById(2L).get());

          repository.saveAll(List.of(c1, c2, c3, c4));
        };
    }
}
