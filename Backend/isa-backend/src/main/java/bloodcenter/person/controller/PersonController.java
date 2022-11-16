package bloodcenter.person.controller;

import bloodcenter.core.ErrorResponse;
import bloodcenter.person.dto.PersonDTO;
import bloodcenter.person.model.Person;
import bloodcenter.person.model.User;
import bloodcenter.person.service.BCAdminService;
import bloodcenter.person.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api/person")
public class PersonController {

    @Autowired
    private PersonService personService;

    @GetMapping("/{id}")
    public @ResponseBody PersonDTO getPersonById(@PathVariable("id") Long id) throws Person.PersonNotFoundException {
        return personService.findById(id);
    }
}
