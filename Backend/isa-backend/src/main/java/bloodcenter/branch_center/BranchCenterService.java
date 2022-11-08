package bloodcenter.branch_center;

import bloodcenter.branch_center.dto.RegisterBranchCenterDTO;
import bloodcenter.core.Address;
import bloodcenter.branch_center.dto.BranchCenterDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

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


    public void updateData(BranchCenterDTO dto) throws BranchCenter.BCNotFoundException {
        Optional<BranchCenter> o_bc = branchCenterRepository.findById(dto.id);
        if(o_bc.isEmpty()){
            throw new BranchCenter.BCNotFoundException("Branch center not found");
        }
        BranchCenter bc = o_bc.get();
        bc.setAddress(dto.address);
        bc.setName(dto.name);
        bc.setDescription(dto.description);

        branchCenterRepository.save(bc);
    }
}
