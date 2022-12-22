package bloodcenter.questionnaire.controller;

import bloodcenter.questionnaire.dto.AnsweredQuestionnaireDTO;
import bloodcenter.questionnaire.dto.QuestionnaireDTO;
import bloodcenter.questionnaire.model.Question;
import bloodcenter.questionnaire.model.Questionnaire;
import bloodcenter.questionnaire.service.QuestionnaireService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("api/questionnaire")
public class QuestionnaireController {

    private final QuestionnaireService questionnaireService;

    public QuestionnaireController(QuestionnaireService questionnaireService) {
        this.questionnaireService = questionnaireService;
    }

    @GetMapping("/get/male/{name}")
    public ResponseEntity<List<Question>> getMaleQuestionnaire(@PathVariable String name) {
        return new ResponseEntity<>(questionnaireService.getMaleQuestions(name), HttpStatus.OK);
    }

    @GetMapping("/get/female/{name}")
    public ResponseEntity<List<Question>> getFemaleQuestionnaire(@PathVariable String name) {
        return new ResponseEntity<>(questionnaireService.getFemaleQuestions(name), HttpStatus.OK);
    }

    @GetMapping("/get/user/{id}")
    public ResponseEntity<AnsweredQuestionnaireDTO> getAnsweredQuestionnaireByUserId(@PathVariable Long id) {
        return new ResponseEntity<>(questionnaireService.getAnsweredQuestionnaireByUserId(id), HttpStatus.OK);
    }

    @PostMapping("/submit")
    public ResponseEntity<?> submitQuestionnaire(@RequestBody QuestionnaireDTO dto) {
        questionnaireService.submitQuestionnaire(dto);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
