package bloodcenter;

import bloodcenter.address.AddressRepository;
import bloodcenter.blood.BloodType;
import bloodcenter.blood.Blood;
import bloodcenter.api_key.Key;
import bloodcenter.branch_center.BranchCenter;
import bloodcenter.branch_center.BranchCenterRepository;
import bloodcenter.address.Address;
import bloodcenter.user.model.BCAdmin;
import bloodcenter.blood.BloodRepository;
import bloodcenter.api_key.KeyRepository;
import bloodcenter.user.model.User;
import bloodcenter.user.repository.BCAdminRepository;
import bloodcenter.user.repository.UserRepository;
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
    CommandLineRunner BCAdminCLR(BCAdminRepository repository, BranchCenterRepository bc_repo) {
        return args -> {
            BCAdmin a1 = new BCAdmin("Rade", "Stojanovic", "stojanovicrade614@gmail.com");
            BCAdmin a2 = new BCAdmin("Darko", "Selakovic", "darko123@gmail.com");
            BCAdmin a3 = new BCAdmin("Vojin", "Bjelica", "vojin123@gmail.com");
            BCAdmin a4 = new BCAdmin("Marko", "Uljarevic", "marko123@gmail.com");

            a1.setBranchCenter(bc_repo.findById(1L).get());
            a2.setBranchCenter(bc_repo.findById(1L).get());
            a3.setBranchCenter(bc_repo.findById(2L).get());

            repository.saveAll(List.of(a1, a2, a3, a4));
        };
    }

    @Bean
    CommandLineRunner UserCLR(UserRepository repository){
        return args -> {
            User u1 = new User("Rade", "Stojanovic", "stojanovicrade614@gmail.com");
            User u2 = new User("Darko", "Selakovic", "darko123@gmail.com");
            User u3 = new User("Vojin", "Bjelica", "vojin123@gmail.com");
            User u4 = new User("Marko", "Uljarevic", "marko123@gmail.com");

            repository.saveAll(List.of(u1, u2, u3, u4));
        };
    }
}
