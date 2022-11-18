package bloodcenter;

import bloodcenter.address.AddressRepository;
import bloodcenter.blood.BloodType;
import bloodcenter.blood.Blood;
import bloodcenter.api_key.Key;
import bloodcenter.branch_center.BranchCenter;
import bloodcenter.branch_center.BranchCenterRepository;
import bloodcenter.address.Address;
import bloodcenter.feedback.Feedback;
import bloodcenter.feedback.FeedbackRepository;
import bloodcenter.person.model.Admin;
import bloodcenter.person.repository.AdminRepository;
import bloodcenter.role.Role;
import bloodcenter.role.RoleRepository;
import bloodcenter.person.model.BCAdmin;
import bloodcenter.blood.BloodRepository;
import bloodcenter.api_key.KeyRepository;
import bloodcenter.person.model.User;
import bloodcenter.person.repository.BCAdminRepository;
import bloodcenter.person.repository.UserRepository;
import bloodcenter.person.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;
import java.util.List;

@Configuration
public class BloodConfiguration {
    @Bean
    CommandLineRunner BloodCLR(BloodRepository repository) {
        return args -> {
            Blood blood1 = new Blood(BloodType.ABPositive, 32.5f);
            Blood blood2 = new Blood(BloodType.ANegative, 5.0f);
            Blood blood3 = new Blood(BloodType.ONegative, 50.2f);
            Blood blood4 = new Blood(BloodType.BPositive, 17.3f);

            repository.saveAll(List.of(blood1, blood2, blood3, blood4));
        };
    }

    @Bean
    CommandLineRunner keyCLR(KeyRepository repository) {
        return args -> {
            Key key1 = new Key("mail_1", "kljuc_1");
            Key key2 = new Key("mail_2", "kljuc_2");

            repository.saveAll(List.of(key1, key2));
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
            Address ad5 = new Address(48.203554, 16.350535, "Stuckgasse", "26", "Wien", "Austira");
            repository.saveAll(List.of(ad1, ad2, ad3,ad4,ad5));
        };
    }

    @Bean
    CommandLineRunner BranchCenterCLR(BranchCenterRepository repository, AddressRepository address_repo) {
        return args -> {
            BranchCenter c1 = new BranchCenter("Happy", "Najbolji novosadski centar");
            BranchCenter c2 = new BranchCenter("Lab", "Dobrovoljno davanje krvi - Beograd");
            BranchCenter c3 = new BranchCenter("Euro lab", "Don de sang volontaire - Paris");
            BranchCenter c4 = new BranchCenter("Jacobs lab", "Freiwillige Blutspende Deutschland");
            BranchCenter c5 = new BranchCenter("Blood - Wien", "Freiwillige Blutspende Österreich");

            c1.setAddress(address_repo.findById(1L).get());
            c2.setAddress(address_repo.findById(2L).get());
            c3.setAddress(address_repo.findById(3L).get());
            c4.setAddress(address_repo.findById(4L).get());
            c5.setAddress(address_repo.findById(5L).get());

            repository.saveAll(List.of(c1, c2, c3, c4, c5));
        };
    }

    @Bean
    CommandLineRunner BCAdminCLR(BCAdminRepository repository, BranchCenterRepository bc_repo, RoleRepository role_repo) {
        return args -> {
            BCAdmin a1 = new BCAdmin("Rade", "Stojanovic", "stojanovicrade614@gmail.com", "$2a$10$2WkfD1m/Ff5ZsB7JClTLfemMsAWzzaGPXoYFKlMY725YHcApCG8Je");
            BCAdmin a2 = new BCAdmin("Darko", "Selakovic", "darko123@gmail.com", "$2a$10$2WkfD1m/Ff5ZsB7JClTLfemMsAWzzaGPXoYFKlMY725YHcApCG8Je");
            BCAdmin a3 = new BCAdmin("Vojin", "Bjelica", "vojin123@gmail.com", "$2a$10$2WkfD1m/Ff5ZsB7JClTLfemMsAWzzaGPXoYFKlMY725YHcApCG8Je");
            BCAdmin a4 = new BCAdmin("Marko", "Uljarevic", "marko123@gmail.com", "$2a$10$2WkfD1m/Ff5ZsB7JClTLfemMsAWzzaGPXoYFKlMY725YHcApCG8Je");

            a1.setBranchCenter(bc_repo.findById(1L).get());
            a2.setBranchCenter(bc_repo.findById(1L).get());
            a3.setBranchCenter(bc_repo.findById(2L).get());

            Role role = role_repo.findByName("ROLE_BCADMIN");
            a1.getRoles().add(role);
            a2.getRoles().add(role);
            a3.getRoles().add(role);
            a4.getRoles().add(role);

            repository.saveAll(List.of(a1, a2, a3, a4));
        };
    }

    @Bean
    CommandLineRunner AdminCLR(AdminRepository repository, RoleRepository role_repo) {
        return args -> {
            Admin a1 = new Admin("Elon", "Musk", "elonmusk@tesla.com", "$2a$10$2WkfD1m/Ff5ZsB7JClTLfemMsAWzzaGPXoYFKlMY725YHcApCG8Je");
            Role role = role_repo.findByName("ROLE_ADMIN");
            a1.getRoles().add(role);
            repository.saveAll(List.of(a1));
        };
    }

    @Bean
    CommandLineRunner UserCLR(UserRepository repository, RoleRepository role_repo) {
        return args -> {
            User u1 = new User("Rade", "Stojanovic", "rade@gmail.com", "$2a$10$2WkfD1m/Ff5ZsB7JClTLfemMsAWzzaGPXoYFKlMY725YHcApCG8Je");
            User u2 = new User("Darko", "Selakovic", "darko@gmail.com", "$2a$10$2WkfD1m/Ff5ZsB7JClTLfemMsAWzzaGPXoYFKlMY725YHcApCG8Je");
            User u3 = new User("Vojin", "Bjelica", "vojin@gmail.com", "$2a$10$2WkfD1m/Ff5ZsB7JClTLfemMsAWzzaGPXoYFKlMY725YHcApCG8Je");
            User u4 = new User("Marko", "Uljarevic", "marko@gmail.com", "$2a$10$2WkfD1m/Ff5ZsB7JClTLfemMsAWzzaGPXoYFKlMY725YHcApCG8Je");

            Role role = role_repo.findByName("ROLE_USER");
            u1.getRoles().add(role);
            u2.getRoles().add(role);
            u3.getRoles().add(role);
            u4.getRoles().add(role);

            repository.saveAll(List.of(u1, u2, u3, u4));
        };
    }

    @Bean
    CommandLineRunner FeedbackCLR(FeedbackRepository repository, UserRepository user_repo, BranchCenterRepository bc_repo){
        return args -> {
            Feedback f1 = new Feedback("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.", LocalDateTime.now(), 5);
            Feedback f2 = new Feedback("Sit amet commodo nulla facilisi nullam. Bibendum arcu vitae elementum curabitur vitae nunc sed velit dignissim. Amet massa vitae tortor condimentum lacinia quis vel eros donec.", LocalDateTime.now(), 3);
            Feedback f3 = new Feedback("Adipiscing tristique risus nec feugiat in fermentum posuere urna nec.", LocalDateTime.now(), 4);
            Feedback f4 = new Feedback(" Ultrices in iaculis nunc sed. Convallis tellus id interdum velit laoreet id donec ultrices. Egestas sed tempus urna et pharetra pharetra.", LocalDateTime.now(), 3);

            Feedback f5 = new Feedback("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.", LocalDateTime.now(), 5);
            Feedback f6 = new Feedback("Sit amet commodo nulla facilisi nullam. Bibendum arcu vitae elementum curabitur vitae nunc sed velit dignissim. Amet massa vitae tortor condimentum lacinia quis vel eros donec.", LocalDateTime.now(), 5);
            Feedback f7 = new Feedback("Adipiscing tristique risus nec feugiat in fermentum posuere urna nec.", LocalDateTime.now(), 5);
            Feedback f8 = new Feedback(" Ultrices in iaculis nunc sed. Convallis tellus id interdum velit laoreet id donec ultrices. Egestas sed tempus urna et pharetra pharetra.", LocalDateTime.now(), 2);

            f1.setUser(user_repo.findByEmail("vojin@gmail.com"));
            f2.setUser(user_repo.findByEmail("rade@gmail.com"));
            f3.setUser(user_repo.findByEmail("vojin@gmail.com"));
            f4.setUser(user_repo.findByEmail("darko@gmail.com"));
            f5.setUser(user_repo.findByEmail("marko@gmail.com"));
            f6.setUser(user_repo.findByEmail("rade@gmail.com"));
            f7.setUser(user_repo.findByEmail("marko@gmail.com"));
            f8.setUser(user_repo.findByEmail("darko@gmail.com"));

            f1.setBranchCenter(bc_repo.findById(1L).get());
            f2.setBranchCenter(bc_repo.findById(1L).get());
            f3.setBranchCenter(bc_repo.findById(1L).get());
            f4.setBranchCenter(bc_repo.findById(1L).get());
            f5.setBranchCenter(bc_repo.findById(1L).get());
            f6.setBranchCenter(bc_repo.findById(1L).get());
            f7.setBranchCenter(bc_repo.findById(1L).get());
            f8.setBranchCenter(bc_repo.findById(1L).get());

            repository.saveAll(List.of(f1, f2, f3, f4, f5, f6, f7, f8));
        };
    }
}
