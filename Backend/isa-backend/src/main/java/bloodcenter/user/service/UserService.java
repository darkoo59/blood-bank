package bloodcenter.user.service;

import bloodcenter.user.dto.RegisterDTO;
import bloodcenter.user.model.User;
import bloodcenter.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private UserRepository userRepository;

    public UserService(@Autowired UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAll() { return this.userRepository.findAll(); }
    public boolean registerUser(RegisterDTO registerDTO) {
        return true;
    }
}
