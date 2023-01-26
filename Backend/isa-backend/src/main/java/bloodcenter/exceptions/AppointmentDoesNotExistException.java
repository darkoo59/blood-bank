package bloodcenter.exceptions;

public class AppointmentDoesNotExistException extends Exception {
    public AppointmentDoesNotExistException() {
        super("Appointment with provided ID does not exist");
    }
}
