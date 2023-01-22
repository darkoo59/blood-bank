package bloodcenter.exceptions;

public class QuestionnaireNotCompleted extends Exception {
    public QuestionnaireNotCompleted() {
        super("User has not completed the questionnaire");
    }
}
