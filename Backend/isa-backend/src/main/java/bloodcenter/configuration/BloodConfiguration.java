package bloodcenter.configuration;

import bloodcenter.enums.BloodType;
import bloodcenter.model.Blood;
import bloodcenter.repository.BloodRepository;
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
                    BloodType.ABpos,
                    32.5f
            );

            Blood blood2 = new Blood(
                    BloodType.Aneg,
                    5.0f
            );

            Blood blood3 = new Blood(
                    BloodType.Oneg,
                    50.2f
            );

            Blood blood4 = new Blood(
                    BloodType.Bpos,
                    17.3f
            );

            repository.saveAll(List.of(blood1, blood2, blood3, blood4));
        };
    }
}
