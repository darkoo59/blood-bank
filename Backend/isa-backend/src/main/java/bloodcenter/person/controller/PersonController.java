package bloodcenter.person.controller;

import bloodcenter.person.dto.ChangePasswordDTO;
import bloodcenter.person.dto.PersonDTO;
import bloodcenter.person.model.Person;
import bloodcenter.person.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api/person")
public class PersonController {

    private final PersonService personService;

    @Autowired
    public PersonController(PersonService personService){
        this.personService = personService;
    }

    @GetMapping("/{id}")
    public @ResponseBody PersonDTO getPersonById(@PathVariable("id") Long id) throws Person.PersonNotFoundException {
        return personService.findById(id);
    }

    @PatchMapping("password")
    public ResponseEntity<Object> changePassword(@RequestBody ChangePasswordDTO dto) {



        System.out.println(dto.getOldPassword());
        System.out.println(dto.getNewPassword());
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
