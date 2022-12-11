package bloodcenter.blood;

import bloodcenter.api_key.KeyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BloodService {
    private final BloodRepository bloodRepository;

    @Autowired
    public BloodService(BloodRepository bloodRepository){
        this.bloodRepository = bloodRepository;
    }

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

    public void saveBlood(List<Blood> bloodToSave){
        bloodRepository.saveAll(bloodToSave);
    }
}
