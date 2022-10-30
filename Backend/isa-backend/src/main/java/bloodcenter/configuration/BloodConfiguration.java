package bloodcenter.configuration;

import bloodcenter.enums.BloodType;
import bloodcenter.model.Blood;
import bloodcenter.model.Key;
import bloodcenter.repository.BloodRepository;
import bloodcenter.repository.KeyRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class BloodConfiguration {
    @Bean
    CommandLineRunner commandLineRunner(BloodRepository repository) {
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
    CommandLineRunner commandLineRunner2(KeyRepository repository) {
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
}
