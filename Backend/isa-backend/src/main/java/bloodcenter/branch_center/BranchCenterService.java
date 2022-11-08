package bloodcenter.branch_center;

import bloodcenter.branch_center.dto.RegisterBranchCenterDTO;
import bloodcenter.core.Address;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BranchCenterService {
    @Autowired
    private final BranchCenterRepository branchCenterRepository;

    public BranchCenterService(BranchCenterRepository branchCenterRepository) {
        this.branchCenterRepository = branchCenterRepository;
    }

    public void registerBranchCenter(RegisterBranchCenterDTO bcDTO) {
        Address address = new Address(bcDTO.street, bcDTO.number, bcDTO.city, bcDTO.country);
        BranchCenter bc = new BranchCenter(bcDTO.name, bcDTO.description, address);
        branchCenterRepository.save(bc);
        //TODO: U repository-ju adrese prvo pozovi da se sacuva adresa, i onda tu adresu ovdje sacuvaj
    }
}
