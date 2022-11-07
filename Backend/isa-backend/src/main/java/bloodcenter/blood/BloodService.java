package bloodcenter.blood;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BloodService {

    @Autowired
    private BloodRepository bloodRepository;

    public List<Blood> getBlood() {
        return bloodRepository.findAll();
    }

    public boolean getBloodType(BloodType bloodType) {
        Optional<Blood> bloodByType = bloodRepository.findBloodByType(bloodType);
        if (bloodByType.isPresent()) {
            return true;
        }
        return false;
    }

    public boolean getBloodTypeQuantity(BloodType bloodType, Float quantity) {
        Optional<Blood> bloodByType = bloodRepository.findBloodByType(bloodType);
        if (bloodByType.isPresent() && bloodByType.get().getQuantity() >= quantity) {
            return true;
        }
        return false;
    }
}
