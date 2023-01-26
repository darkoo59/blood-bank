package simulator.exceptions;

public class ServiceCurrentlyUnavailable extends Exception {
    public ServiceCurrentlyUnavailable() {
        super("Location simulator service is currently unavailable");
    }
}
