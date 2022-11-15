package bloodcenter.person.service;

import bloodcenter.person.dto.BCAdminDTO;
import bloodcenter.person.dto.RegisterBCAdminDTO;
import bloodcenter.person.model.BCAdmin;
import bloodcenter.person.repository.BCAdminRepository;
import bloodcenter.branch_center.BranchCenter;
import bloodcenter.branch_center.BranchCenterService;
import bloodcenter.person.dto.AssignAdminToCenterDTO;
import bloodcenter.utils.ObjectsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class BCAdminService {
    private final BCAdminRepository repository;
    private final BranchCenterService bcService;

    public BCAdminService(@Autowired BCAdminRepository repository, @Autowired BranchCenterService bcService){
        this.repository = repository;
        this.bcService = bcService;
    }

    public BCAdmin getByMail(String email) throws BCAdmin.BCAdminNotFoundException {
        BCAdmin admin = this.repository.findByEmail(email);
        if(admin == null){
            throw new BCAdmin.BCAdminNotFoundException("Branch center admin not found.");
        }
        return admin;
    }

    public ArrayList<BCAdminDTO> getUnassignedAdmins() {
        ArrayList<BCAdminDTO> ret = new ArrayList<BCAdminDTO>();
        repository.findAll();
        for (BCAdmin admin : repository.findAll()) {
            if (admin.getBranchCenter() == null) {
                ret.add(ObjectsMapper.convertBCAdminToDTO(admin));
            }
        }
        return ret;
    }

    public void registerBCAdmin(RegisterBCAdminDTO bcaDTO) throws BCAdmin.BCAdminEmailTakenException {
        BCAdmin admin = new BCAdmin(bcaDTO.name, bcaDTO.surname, bcaDTO.email, bcaDTO.password);

        BCAdmin existingAdmin = this.repository.findByEmail(bcaDTO.email);
        if (existingAdmin == null) {
            repository.save(admin);
        } else {
            throw new BCAdmin.BCAdminEmailTakenException("User with email " + bcaDTO.email + " already exists.");
        }
    }

    public void assignAdminToCenter(AssignAdminToCenterDTO dto) throws BCAdmin.BCAdminNotFoundException, BranchCenter.BCNotFoundException {
        BCAdmin admin = this.getByMail(dto.bcAdminEmail);
        BranchCenter center = bcService.getById(dto.centerId);

        admin.setBranchCenter(center);
        repository.save(admin);
    }

}
