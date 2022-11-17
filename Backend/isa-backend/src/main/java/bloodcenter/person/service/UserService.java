package bloodcenter.person.service;

import bloodcenter.address.AddressRepository;
import bloodcenter.person.model.Person;
import bloodcenter.person.repository.AdminRepository;
import bloodcenter.person.repository.BCAdminRepository;
import bloodcenter.role.Role;
import bloodcenter.role.RoleRepository;
import bloodcenter.person.dto.RegisterDTO;
import bloodcenter.person.model.User;
import bloodcenter.person.repository.UserRepository;
import bloodcenter.utils.ObjectsMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;
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
}
