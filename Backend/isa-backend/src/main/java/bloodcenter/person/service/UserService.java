package bloodcenter.person.service;

import bloodcenter.address.AddressRepository;
import bloodcenter.person.model.Person;
import bloodcenter.role.Role;
import bloodcenter.role.RoleRepository;
import bloodcenter.person.dto.RegisterDTO;
import bloodcenter.person.model.User;
import bloodcenter.person.repository.UserRepository;
import bloodcenter.utils.ObjectsMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final AddressRepository addressRepository;
    private final PasswordEncoder passwordEncoder;

    public void saveUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    public void addRoleToUser(String email, String roleName) {
        User user = userRepository.findByEmail(email);
        Role role = roleRepository.findByName(roleName);
        user.getRoles().add(role);
    }

    public User getUser(String email) {
        return userRepository.findByEmail(email);
    }

    public List<User> getFirst(int number) {
        return userRepository.findAll(Pageable.ofSize(number)).toList();
    }

    public List<User> getAll() { return this.userRepository.findAll(); }

    public boolean registerUser(RegisterDTO registerDTO) {
        User user = ObjectsMapper.convertRegisterDTOToUser(registerDTO);
        if (user == null) return false;
        Role role = roleRepository.findByName("ROLE_USER");
        if (role == null) {
             role = new Role(0, "ROLE_USER");
             roleRepository.save(role);
        }
        addressRepository.save(user.getAddress());
        saveUser(user);
        addRoleToUser(user.getEmail(), role.getName());
        return true;
    }

    public List<User> searchUsers(String searchInput) {
        List<User> ret = userRepository.searchUsers(searchInput.toLowerCase());
        return ret;
    }

    public void update(Person personToUpdate) {
        User userToUpdate = getUser(personToUpdate.getEmail());
        userToUpdate.setPassword(personToUpdate.getPassword());
        userToUpdate.setAddress(personToUpdate.getAddress());
        userToUpdate.setEmail(personToUpdate.getEmail());
        userToUpdate.setFirstname(personToUpdate.getFirstname());
        userToUpdate.setInformation(personToUpdate.getInformation());
        userToUpdate.setLastname(personToUpdate.getLastname());
        userToUpdate.setNationalId(personToUpdate.getNationalId());
        userToUpdate.setOccupation(personToUpdate.getOccupation());
        userToUpdate.setPhone(personToUpdate.getPhone());
        userToUpdate.setSex(personToUpdate.getSex());
        userRepository.save(userToUpdate);
    }
}
