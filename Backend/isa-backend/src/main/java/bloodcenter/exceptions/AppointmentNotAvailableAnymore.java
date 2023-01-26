package bloodcenter.exceptions;

public class AppointmentNotAvailableAnymore extends Exception {
    public AppointmentNotAvailableAnymore() {
        super("The appointment has become unavailable in the meantime");
    }
}
