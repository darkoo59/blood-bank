package bloodcenter.person.service;

import bloodcenter.person.model.Admin;
import bloodcenter.person.model.Person;
import bloodcenter.person.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class AdminService {
    private final AdminRepository adminRepository;

    @Autowired
    public AdminService(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
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
}
