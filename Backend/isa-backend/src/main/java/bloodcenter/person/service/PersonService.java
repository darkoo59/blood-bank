package bloodcenter.person.service;

import bloodcenter.address.AddressService;
import bloodcenter.person.dto.ChangePasswordDTO;
import bloodcenter.person.dto.PersonDTO;
import bloodcenter.person.model.BCAdmin;
import bloodcenter.person.model.Person;
import bloodcenter.person.repository.PersonRepository;
import bloodcenter.utils.ObjectsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

import static bloodcenter.utils.ObjectsMapper.*;

@Service
public class PersonService implements UserDetailsService {
    private final PersonRepository personRepository;
    private final UserService userService;
    private final BCAdminService bcAdminService;
    private final AdminService adminService;
    private final PasswordEncoder passwordEncoder;
    private final AddressService addressService;

    @Autowired
    public PersonService(PersonRepository personRepository, UserService userService,
                         BCAdminService bcAdminService, AdminService adminService,
                         PasswordEncoder passwordEncoder, AddressService addressService) {
        this.personRepository = personRepository;
        this.userService = userService;
        this.bcAdminService = bcAdminService;
        this.adminService = adminService;
        this.passwordEncoder = passwordEncoder;
        this.addressService = addressService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Person person = getPerson(username);
        if (person == null) {
            throw new UsernameNotFoundException("User not found in the database");
        }
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        person.getRoles().forEach(role -> authorities.add(new SimpleGrantedAuthority(role.getName())));
        return new org.springframework.security.core.userdetails.User(
                person.getEmail(),
                person.getPassword(),
                authorities) {
            @Override
            public boolean isEnabled() {
                return person.getEnabled();
            }
        };
    }



    public PersonDTO findById(Long id) throws Person.PersonNotFoundException {
        Optional<Person> person = personRepository.findById(id);
        if(person.isEmpty())
            throw new Person.PersonNotFoundException("Person with id = " + id + " isn't found in database!");
        return ObjectsMapper.convertPersonToDTO(person.get());
    }

    public Person getPerson(String email) {
        Person person = userService.getUser(email);
        if (person == null) {
            person = adminService.getAdmin(email);
        }
        if (person == null) {
            try {
                person = bcAdminService.getByMail(email);
            } catch (BCAdmin.BCAdminNotFoundException ignored) {

            }
        }
        return person;
    }

    public PersonDTO getPersonDTOFromEmail(String email) {
        Person person = getPerson(email);
        if (person != null) {
            return convertPersonToPersonDTO(person);
        }
        return null;
    }

    public void updatePerson(PersonDTO personDTO) throws Person.PersonNotFoundException, Person.PersonCantBeUpdatedException, BCAdmin.BCAdminNotFoundException {
        Optional<Person> person = personRepository.findById(personDTO.getId());
        if(person.isEmpty())
            throw new Person.PersonNotFoundException("Person with id = " + personDTO.getId() + " isn't found in database!");
        if(personDTO.address.id == null) {
            addressService.saveAdress(convertDTOToAddress(personDTO.getAddress()));
        }
        Person personToUpdate = person.get();
        personToUpdate.setAddress(addressService.getAddressByLatLng(personDTO.getAddress().getLat(), personDTO.getAddress().getLng()));
        System.out.println(addressService.getAddressByLatLng(personDTO.getAddress().getLat(), personDTO.getAddress().getLng()).getId());
        personToUpdate.setFirstname(personDTO.getFirstname());
        personToUpdate.setInformation(personDTO.getInformation());
        personToUpdate.setLastname(personDTO.getLastname());
        personToUpdate.setNationalId(personDTO.getNationalId());
        personToUpdate.setOccupation(personDTO.getOccupation());
        personToUpdate.setPhone(personDTO.getPhone());
        personToUpdate.setSex(personDTO.getSex());
        personRepository.save(personToUpdate);
//        Collection<Role> personRoles = personToUpdate.getRoles();
//        for (Role role:personRoles) {
//            System.out.println(role.getId());
//            switch(role.getName()){
//                case "ROLE_ADMIN":
//                    adminService.update(personToUpdate);
//                    break;
//                case "ROLE_BCADMIN":
//                    bcAdminService.update(personToUpdate);
//                    break;
//                case "ROLE_USER":
//                    userService.update(personToUpdate);
//                    break;
//                default:
//                    throw new Person.PersonCantBeUpdatedException("Person with id = " + personDTO.getId() + " can't be updated!");
//            }
//        }
    }

    @CacheEvict(value = "Users", allEntries = true)
    public void changePassword(String email, ChangePasswordDTO dto) throws Exception {
        Person person = this.getPerson(email);
        if (person == null) throw new Exception("User not found");
        if (!passwordEncoder.matches(dto.getOldPassword(), person.getPassword()))
            throw new Exception("Old password does not match");

        person.setPassword(passwordEncoder.encode(dto.getNewPassword()));
        personRepository.save(person);
    }

    public void changeAdminPassword(String email, String password) throws Exception {
        Person person = this.getPerson(email);
        if (person == null) throw new Exception("User not found");
        //person.setPassword(passwordEncoder.encode(password));
        person.setPassword(password);
        personRepository.save(person);
        adminService.passwordChanged(email);
    }
}
