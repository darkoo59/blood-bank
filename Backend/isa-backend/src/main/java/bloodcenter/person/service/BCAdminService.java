package bloodcenter.person.service;

import bloodcenter.branch_center.BranchCenter;
import bloodcenter.branch_center.BranchCenterService;
import bloodcenter.branch_center.dto.BranchCenterDTO;
import bloodcenter.person.dto.AssignAdminToCenterDTO;
import bloodcenter.person.dto.BCAdminDTO;
import bloodcenter.person.dto.RegisterBCAdminDTO;
import bloodcenter.person.model.BCAdmin;
import bloodcenter.person.model.Person;
import bloodcenter.person.repository.BCAdminRepository;
import bloodcenter.role.Role;
import bloodcenter.role.RoleRepository;
import bloodcenter.utils.ObjectsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;

@Service
@Transactional
public class BCAdminService {
    private final BCAdminRepository repository;
    private final BranchCenterService bcService;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    public BCAdminService(@Autowired BCAdminRepository repository, @Autowired BranchCenterService bcService,
                          @Autowired PasswordEncoder passwordEncoder, @Autowired RoleRepository roleRepository){
        this.repository = repository;
        this.bcService = bcService;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }

    public BCAdmin getByMail(String email) throws BCAdmin.BCAdminNotFoundException {
        BCAdmin admin = this.repository.findByEmail(email);
        if(admin == null)
            throw new BCAdmin.BCAdminNotFoundException("Branch center admin not found.");
        return admin;
    }

    public void updateBranchCenter(String email, BranchCenterDTO dto) throws Exception{
        BCAdmin admin = this.repository.findByEmail(email);
        if(admin.getBranchCenter() == null)
            throw new BranchCenter.BCNotFoundException("Branch center not found");
        dto.setId(admin.getBranchCenter().getId());
        bcService.updateData(dto);
    }

    public ArrayList<BCAdminDTO> getUnassignedAdmins() {
        ArrayList<BCAdminDTO> ret = new ArrayList<BCAdminDTO>();
        repository.findAll();
        for (BCAdmin admin : repository.findAll()) {
            if (admin.getBranchCenter() == null) {
                ret.add(ObjectsMapper.convertBCAdminToDTO(admin));
            }
        }
        return ret;
    }

    public void addRoleToBCAdmin(String email, String roleName) {
        BCAdmin admin = repository.findByEmail(email);
        System.out.println("NASAO ADMINA: " + admin.getFirstname() + " " + admin.getLastname());
        Role role = roleRepository.findByName(roleName);
        System.out.println("NASAO ROLU: " + role.getName());
        admin.getRoles().add(role);
    }

    public void registerBCAdmin(RegisterBCAdminDTO bcaDTO) throws BCAdmin.BCAdminEmailTakenException {
        BCAdmin admin = new BCAdmin(bcaDTO.name, bcaDTO.surname, bcaDTO.email, bcaDTO.password);

        BCAdmin existingAdmin = this.repository.findByEmail(bcaDTO.email);
        Role role = roleRepository.findByName("ROLE_BCADMIN");
        if (role == null) {
            role = new Role(2, "ROLE_BCADMIN");
            roleRepository.save(role);
        }
        System.out.println("Role: " + role.getName());
        if (existingAdmin == null) {
            admin.setPassword(passwordEncoder.encode(admin.getPassword()));
            repository.save(admin);
            addRoleToBCAdmin(admin.getEmail(), role.getName());
            System.out.println("!!!!!!!!!!!!!!!!!!!" + admin.getRoles().isEmpty());
        } else {
            throw new BCAdmin.BCAdminEmailTakenException("User with email " + bcaDTO.email + " already exists.");
        }
    }

    public void assignAdminToCenter(AssignAdminToCenterDTO dto) throws BCAdmin.BCAdminNotFoundException, BranchCenter.BCNotFoundException {
        BCAdmin admin = this.getByMail(dto.bcAdminEmail);
        BranchCenter center = bcService.getById(dto.centerId);

        admin.setBranchCenter(center);
        repository.save(admin);
    }

    public BranchCenter getBranchCenterByAdminEmail(String email) throws BCAdmin.BCAdminNotFoundException {
        return getByMail(email).getBranchCenter();
    }

    public void update(Person personToUpdate) throws BCAdmin.BCAdminNotFoundException {
        BCAdmin adminToUpdate = getByMail(personToUpdate.getEmail());
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
        repository.save(adminToUpdate);
    }
}
