package bloodcenter.exceptions;

public class UserPenaltiesException extends Exception {
    public UserPenaltiesException() {
        super("The user has been temporarily disabled because they have reached 3 penalties");
    }
}
