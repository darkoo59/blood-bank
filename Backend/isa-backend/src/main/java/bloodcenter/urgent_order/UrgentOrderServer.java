package bloodcenter.urgent_order;

import bloodcenter.blood.BloodService;
import bloodcenter.blood.BloodType;
import com.anubhav.grpc.UrgentOrderServiceGrpc;
import com.anubhav.grpc.UrgentRequest;
import com.anubhav.grpc.UrgentResponse;
import io.grpc.stub.StreamObserver;
import org.lognet.springboot.grpc.GRpcService;
import org.springframework.beans.factory.annotation.Autowired;

@GRpcService
public class UrgentOrderServer extends UrgentOrderServiceGrpc.UrgentOrderServiceImplBase {
    private final BloodService _bloodService;

    @Autowired
    public UrgentOrderServer(BloodService bloodService) {
        this._bloodService = bloodService;
    }
    @Override
    public void invokeUrgentOrder(UrgentRequest request, StreamObserver<UrgentResponse> responseObserver) {
        int bloodType = request.getBloodType();
        float quantity = request.getQuantity();

        com.anubhav.grpc.UrgentResponse urgentResponse;

        System.out.println("Uslo u pozivanje metode");

        if(_bloodService.getBloodTypeQuantity(parseIntToBloodType(bloodType), quantity)) {
            System.out.println("Ima dovoljno krvi");
             urgentResponse = com.anubhav.grpc.UrgentResponse.newBuilder()
                    .setHasEnough(true).build();
        } else {
            System.out.println("Nema dovoljno krvi");
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
