package bloodcenter.user.service;

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

    public BCAdmin getByMail(String email){
        BCAdmin admin = this._repository.findByEmail(email);
        if(admin == null){
            System.out.println("bc admin not found");
        }
        return admin;
    }
}
