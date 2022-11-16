package bloodcenter.person.service;

import bloodcenter.core.ErrorResponse;
import bloodcenter.person.dto.PersonDTO;
import bloodcenter.person.model.Person;
import bloodcenter.person.repository.PersonRepository;
import bloodcenter.role.RoleRepository;
import bloodcenter.utils.ObjectsMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class PersonService {
    private final PersonRepository personRepository;

    public PersonDTO findById(Long id) throws Person.PersonNotFoundException {
        Optional<Person> person = personRepository.findById(id);
        if(person.isEmpty())
            throw new Person.PersonNotFoundException("Person with id = " + id + " isn't found in database!");
        return ObjectsMapper.convertPersonToDTO(person.get());
    }

    @ExceptionHandler({ Exception.class })
    public ResponseEntity<Object> handleExceptions(Exception ex){
        ex.printStackTrace();
        return new ResponseEntity<>(new ErrorResponse(ex.getMessage()), HttpStatus.BAD_REQUEST);
    }
}
