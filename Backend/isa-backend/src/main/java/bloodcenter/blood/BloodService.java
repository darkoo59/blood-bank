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

    public boolean getBloodType(String bloodType) {
        System.out.println(bloodType);
        Optional<Blood> bloodByType = bloodRepository.findBloodByType(convertToEnum(bloodType));
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

    public boolean checkBloodAvailabilityWithQuantity(String bloodType, Float quantity) {
        Optional<Blood> bloodByType = bloodRepository.findBloodByType(convertToEnum(bloodType));
        if (bloodByType.isPresent() && bloodByType.get().getQuantity() >= quantity) {
            return true;
        }
        return false;
    }

    public void saveBlood(List<Blood> bloodToSave){
        bloodRepository.saveAll(bloodToSave);
    }

    private BloodType convertToEnum(String type){
        switch (type){
            case "A_PLUS": return BloodType.APositive;
            case "A_MINUS": return BloodType.ANegative;
            case "B_PLUS": return BloodType.BPositive;
            case "B_MINUS": return BloodType.BNegative;
            case "AB_PLUS": return BloodType.ABPositive;
            case "AB_MINUS": return BloodType.ABNegative;
            case "O_PLUS": return BloodType.OPositive;
            default: return BloodType.ONegative;

        }
    }
}
