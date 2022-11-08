package bloodcenter.branch_center;

import bloodcenter.branch_center.dto.BranchCenterDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BranchCenterService {
    private final BranchCenterRepository _repository;

    public BranchCenterService(@Autowired BranchCenterRepository repo){
        this._repository = repo;
    }


    public void updateData(BranchCenterDTO dto) throws BranchCenter.BCNotFoundException {
        Optional<BranchCenter> o_bc = _repository.findById(dto.id);
        if(o_bc.isEmpty()){
            throw new BranchCenter.BCNotFoundException("Branch center not found");
        }
        BranchCenter bc = o_bc.get();
        bc.getAddress().setCity(dto.address.city);
        bc.getAddress().setStreet(dto.address.street);
        bc.getAddress().setNumber(dto.address.number);
        bc.getAddress().setCountry(dto.address.country);
        //Address a = new Address(dto.address.street, dto.address.number, dto.address.city, dto.address.country);
        //bc.setAddress(a);

        bc.setName(dto.name);
        bc.setDescription(dto.description);

        _repository.save(bc);
    }
}
