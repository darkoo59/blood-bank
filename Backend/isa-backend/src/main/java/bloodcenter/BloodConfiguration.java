package bloodcenter;

import bloodcenter.address.AddressRepository;
import bloodcenter.blood.BloodType;
import bloodcenter.blood.Blood;
import bloodcenter.api_key.Key;
import bloodcenter.branch_center.BranchCenter;
import bloodcenter.branch_center.BranchCenterRepository;
import bloodcenter.address.Address;
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

import java.util.List;

@Configuration
public class BloodConfiguration {
    @Bean
    CommandLineRunner BloodCLR(BloodRepository repository) {
        return args -> {
            Blood blood1 = new Blood(
                    BloodType.ABPositive,
                    32.5f
            );

            Blood blood2 = new Blood(
                    BloodType.ANegative,
                    5.0f
            );

            Blood blood3 = new Blood(
                    BloodType.ONegative,
                    50.2f
            );

            Blood blood4 = new Blood(
                    BloodType.BPositive,
                    17.3f
            );

            repository.saveAll(List.of(blood1, blood2, blood3, blood4));
        };
    }

    @Bean
    CommandLineRunner keyCLR(KeyRepository repository) {
        return args -> {
            Key key1 = new Key(
                    "mail_1",
                    "kljuc_1"
            );

            Key key2 = new Key(
                    "mail_2",
                    "kljuc_2"
            );

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
            Address ad1 = new Address(10, 10, "ulica", "broj", "grad", "drzava");
            Address ad2 = new Address(10, 20, "ulica2", "broj2", "grad2", "drzava2");
            Address ad3 = new Address(20, 30, "ulica3", "broj3", "grad3", "drzava3");

            repository.saveAll(List.of(ad1, ad2, ad3));
        };
    }

    @Bean
    CommandLineRunner BranchCenterCLR(BranchCenterRepository repository, AddressRepository address_repo) {
        return args -> {
            BranchCenter c1 = new BranchCenter("Centar 1", "lorem ipsum 1");
            BranchCenter c2 = new BranchCenter("Centar 2", "lorem ipsum 2");
            BranchCenter c3 = new BranchCenter("Centar 3", "lorem ipsum 3");

            c1.setAddress(address_repo.findById(1L).get());
            c2.setAddress(address_repo.findById(2L).get());
            c3.setAddress(address_repo.findById(3L).get());

            repository.saveAll(List.of(c1, c2, c3));
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
    CommandLineRunner PersonRolesCLR(UserService userService) {
        return args -> {
//            userService.addRoleToUser("rade@gmail.com", "ROLE_USER");
//            userService.addRoleToUser("darko@gmail.com", "ROLE_USER");
        };
    }
}
