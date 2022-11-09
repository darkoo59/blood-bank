package bloodcenter.user.service;

import bloodcenter.user.dto.RegisterDTO;
import bloodcenter.user.model.User;
import bloodcenter.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private UserRepository userRepository;

    public UserService(@Autowired UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean registerUser(RegisterDTO registerDTO) {
        return true;
    }
}
