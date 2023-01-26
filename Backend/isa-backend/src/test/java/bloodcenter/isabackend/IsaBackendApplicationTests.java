package bloodcenter.isabackend;

import bloodcenter.available_appointment.AvailableAppointment;
import bloodcenter.available_appointment.AvailableAppointmentService;
import bloodcenter.available_appointment.dto.AvailableAppointmentsDTO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@SpringBootTest
@RunWith(SpringRunner.class)
public class IsaBackendApplicationTests {
    @Autowired
    private AvailableAppointmentService availableAppointmentService;
    @Test(expected = Exception.class)
    public void testConcurrentAppointmentCreation() throws Throwable {
        String email = "rade123@gmail.com";
        String start = "Mon, 23 Jan 2023 10:30:00 GMT";
        String end = "Mon, 23 Jan 2023 11:00:00 GMT";
        String title = "2";

        ExecutorService executor = Executors.newFixedThreadPool(2);
        executor.submit(() -> {
            AvailableAppointmentsDTO dto1 = new AvailableAppointmentsDTO(1L, title, start, end, 1L);
            try {
                availableAppointmentService.create(email, dto1);
                for(AvailableAppointment app: availableAppointmentService.getAllUnauthorized()){
                    System.out.println(app.getTitle());
                }
                System.out.println("1 created");
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        });
        Future<?> future2 = executor.submit(() -> {
            try { Thread.sleep(50); } catch (InterruptedException ignored) { }
            AvailableAppointmentsDTO dto2 = new AvailableAppointmentsDTO(2L, title, start, end, 1L);
            try {
                availableAppointmentService.create(email, dto2);
                for(AvailableAppointment app: availableAppointmentService.getAllUnauthorized()){
                    System.out.println(app.getTitle());
                }
                System.out.println("2 created");
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        });
        try {
            future2.get();
        } catch (ExecutionException e) {
            //System.out.println("Exception from thread " + e.getCause().getClass());
            throw e.getCause();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        executor.shutdown();
    }

}
