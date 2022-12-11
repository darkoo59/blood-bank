package bloodcenter.urgent_order;

import bloodcenter.blood.Blood;
import bloodcenter.blood.BloodRepository;
import bloodcenter.blood.BloodService;
import bloodcenter.blood.BloodType;
import com.anubhav.grpc.UrgentOrderServiceGrpc;
import com.anubhav.grpc.UrgentRequest;
import com.anubhav.grpc.UrgentResponse;
import io.grpc.stub.StreamObserver;
import org.lognet.springboot.grpc.GRpcService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

@GRpcService
public class UrgentOrderServer extends UrgentOrderServiceGrpc.UrgentOrderServiceImplBase {
    private final BloodService _bloodService;
    private BloodRepository _bloodRepository;
    @Autowired
    public UrgentOrderServer(BloodService bloodService, BloodRepository bloodRepository) {
        this._bloodService = bloodService;
        this._bloodRepository = bloodRepository;
    }
    @Override
    public void invokeUrgentOrder(UrgentRequest request, StreamObserver<UrgentResponse> responseObserver) {
        int bloodType = request.getBloodType();
        float quantity = request.getQuantity();

        com.anubhav.grpc.UrgentResponse urgentResponse;

        if(_bloodService.getBloodTypeQuantity(parseIntToBloodType(bloodType), quantity)) {
            Optional<Blood> blood = _bloodRepository.findBloodByType(parseIntToBloodType(bloodType));

            if (blood.isPresent()) {
                blood.get().setQuantity(blood.get().getQuantity() - (Float)quantity);
                _bloodRepository.save(blood.get());
            }

             urgentResponse = com.anubhav.grpc.UrgentResponse.newBuilder()
                    .setHasEnough(true).build();

        } else {
             urgentResponse = com.anubhav.grpc.UrgentResponse.newBuilder()
                    .setHasEnough(false).build();
        }

        responseObserver.onNext(urgentResponse);

        responseObserver.onCompleted();
    }

    public BloodType parseIntToBloodType(int number) {
        switch(number) {
            case 0: return BloodType.APositive;
            case 1: return BloodType.ANegative;
            case 2: return BloodType.BPositive;
            case 3: return BloodType.BNegative;
            case 4: return BloodType.OPositive;
            case 5: return BloodType.ONegative;
            case 6: return BloodType.ABPositive;
            case 7: return BloodType.ABNegative;
            default: return null;
        }
    }
}
