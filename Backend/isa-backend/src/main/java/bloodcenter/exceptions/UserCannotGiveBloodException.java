package bloodcenter.exceptions;

public class UserCannotGiveBloodException extends Exception {
    public UserCannotGiveBloodException() {
        super("User has already given blood within the last six months");
    }
}
