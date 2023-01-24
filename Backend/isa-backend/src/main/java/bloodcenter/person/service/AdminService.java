package bloodcenter.person.service;

import bloodcenter.address.AddressRepository;
import bloodcenter.person.dto.RegisterAdminDTO;
import bloodcenter.person.model.Admin;
import bloodcenter.person.model.Person;
import bloodcenter.person.repository.AdminRepository;
import bloodcenter.role.Role;
import bloodcenter.role.RoleRepository;
import bloodcenter.utils.ObjectsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class AdminService {
    private final AdminRepository adminRepository;
    private final RoleRepository roleRepository;
    private final AddressRepository addressRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AdminService(AdminRepository adminRepository, RoleRepository roleRepository,
                        AddressRepository addressRepository, PasswordEncoder passwordEncoder) {
        this.adminRepository = adminRepository;
        this.roleRepository = roleRepository;
        this.addressRepository = addressRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Admin getAdmin(String email) {
        return adminRepository.findByEmail(email);
    }

    public void update(Person personToUpdate) {
        Admin adminToUpdate = getAdmin(personToUpdate.getEmail());
        adminToUpdate.setPassword(personToUpdate.getPassword());
        adminToUpdate.setAddress(personToUpdate.getAddress());
        adminToUpdate.setEmail(personToUpdate.getEmail());
        adminToUpdate.setFirstname(personToUpdate.getFirstname());
        adminToUpdate.setInformation(personToUpdate.getInformation());
        adminToUpdate.setLastname(personToUpdate.getLastname());
        adminToUpdate.setNationalId(personToUpdate.getNationalId());
        adminToUpdate.setOccupation(personToUpdate.getOccupation());
        adminToUpdate.setPhone(personToUpdate.getPhone());
        adminToUpdate.setSex(personToUpdate.getSex());
        adminRepository.save(adminToUpdate);
    }

    public void saveAdmin(Admin admin) {
        admin.setPassword(passwordEncoder.encode(admin.getPassword()));
        adminRepository.save(admin);
    }
    public void addRoleToAdmin(String email, String roleName) {
        Admin admin = adminRepository.findByEmail(email);
        Role role = roleRepository.findByName(roleName);
        admin.getRoles().add(role);
    }

    public boolean register(RegisterAdminDTO dto) {
        Admin admin = ObjectsMapper.convertRegisterAdminDTOToAdmin(dto);
        if (admin == null) return false;
        Role role = roleRepository.findByName("ROLE_ADMIN");
        if (role == null) {
            role = new Role(0, "ROLE_ADMIN");
            roleRepository.save(role);
        }
        addressRepository.save(admin.getAddress());
        admin.setPassword("adminpassword");
        admin.setEnabled(true);
        saveAdmin(admin);
        addRoleToAdmin(admin.getEmail(), role.getName());
        return true;
    }

    public void passwordChanged(String email) {
        Admin admin = adminRepository.findByEmail(email);
        admin.setPasswordChanged(true);
        saveAdmin(admin);
    }

}
