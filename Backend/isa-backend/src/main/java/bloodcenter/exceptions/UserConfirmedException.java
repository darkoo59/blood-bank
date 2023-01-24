package bloodcenter.exceptions;

public class UserConfirmedException extends Exception {
    public UserConfirmedException() {
        super("User already confirmed");
    }
}
