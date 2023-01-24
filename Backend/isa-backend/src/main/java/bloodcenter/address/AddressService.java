package bloodcenter.address;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public Address getAddressByLatLng(double lat, double lng){ return _repository.getAddressByLatLng(lat,lng); }
    public List<String> getAllCountries(){return _repository.getAllCountries();}
    public List<String> getAllCities(){return _repository.getAllCities();}
}
