package bloodcenter.address;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AddressService {

    private final AddressRepository _repository;
    @Autowired
    public AddressService(AddressRepository addressRepository) {
        this._repository = addressRepository;
    }

    public void saveAdress(Address address){
        _repository.save(address);
    }
}
