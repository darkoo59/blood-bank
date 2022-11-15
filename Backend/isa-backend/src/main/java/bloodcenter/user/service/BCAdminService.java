package bloodcenter.user.service;

import bloodcenter.branch_center.BranchCenter;
import bloodcenter.branch_center.BranchCenterService;
import bloodcenter.core.ErrorResponse;
import bloodcenter.user.dto.AssignAdminToCenterDTO;
import bloodcenter.user.dto.BCAdminDTO;
import bloodcenter.user.dto.RegisterBCAdminDTO;
import bloodcenter.user.model.BCAdmin;
import bloodcenter.user.repository.BCAdminRepository;
import bloodcenter.utils.ObjectsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ExceptionHandler;

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
