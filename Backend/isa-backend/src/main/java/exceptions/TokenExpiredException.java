package exceptions;

public class TokenExpiredException extends Exception {
    public TokenExpiredException() {
        super("Confirmation token already expired");
    }
}
