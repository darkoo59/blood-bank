package bloodcenter.exceptions;

public class EmailExistsException extends Exception {
    public EmailExistsException() {
        super("Email is already taken");
    }
}
