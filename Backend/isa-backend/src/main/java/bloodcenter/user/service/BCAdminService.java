package bloodcenter.user.service;

import bloodcenter.user.model.BCAdmin;
import bloodcenter.user.repository.BCAdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BCAdminService {
    private final BCAdminRepository repository;

    public BCAdminService(@Autowired BCAdminRepository repository){
        this.repository = repository;
    }

    public BCAdmin getByMail(String email) throws BCAdmin.BCAdminNotFoundException {
        BCAdmin admin = this.repository.findByEmail(email);
        if(admin == null){
            throw new BCAdmin.BCAdminNotFoundException("Branch center admin not found.");
        }
        return admin;
    }
}
