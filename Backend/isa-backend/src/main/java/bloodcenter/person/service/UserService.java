package bloodcenter.person.service;

import bloodcenter.address.AddressRepository;
import bloodcenter.role.Role;
import bloodcenter.role.RoleRepository;
import bloodcenter.person.dto.RegisterDTO;
import bloodcenter.person.model.User;
import bloodcenter.person.repository.UserRepository;
import bloodcenter.utils.ObjectsMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final AddressRepository addressRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found in the database");
        }
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        user.getRoles().forEach(role -> authorities.add(new SimpleGrantedAuthority(role.getName())));
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), authorities);
    }

    public void saveUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    public Role saveRole(Role role) {
        return roleRepository.save(role);
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
        addressRepository.save(user.getAddress());
        saveUser(user);
        return true;
    }
}
