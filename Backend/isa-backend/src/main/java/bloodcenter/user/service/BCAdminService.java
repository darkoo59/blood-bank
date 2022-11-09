package bloodcenter.user.service;

import bloodcenter.user.dto.RegisterBCAdminDTO;
import bloodcenter.user.model.BCAdmin;
import bloodcenter.user.repository.BCAdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BCAdminService {
    private final BCAdminRepository _repository;

    public BCAdminService(@Autowired BCAdminRepository repository){
        this._repository = repository;
    }

    public BCAdmin getByMail(String email) throws BCAdmin.BCAdminNotFoundException {
        BCAdmin admin = this._repository.findByEmail(email);
        if(admin == null){
            throw new BCAdmin.BCAdminNotFoundException("Branch center admin not found.");
        }
        return admin;
    }

    public void registerBCAdmin(RegisterBCAdminDTO bcaDTO) {
        BCAdmin admin = new BCAdmin(bcaDTO.name, bcaDTO.surname, bcaDTO.email, bcaDTO.password);
        _repository.save(admin);
    }
}
