package exceptions;

public class TokenNotFoundException extends Exception {
    public TokenNotFoundException() {
        super(("Confirmation token was not found"));
    }
}
