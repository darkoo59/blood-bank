package bloodcenter.exceptions;

public class CancellationTooLateException extends Exception {
    public CancellationTooLateException() {
        super("Cancellation of this appointment is restricted as it's less than 24 hours away.");
    }
}
