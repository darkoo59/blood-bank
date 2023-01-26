package bloodcenter.isabackend;

import bloodcenter.complaint.Complaint;
import bloodcenter.complaint.ComplaintService;
import bloodcenter.complaint.dto.ComplaintResponseDTO;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.test.context.junit4.SpringRunner;

import javax.mail.MessagingException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ComplaintResponseTest {

    @Autowired
    private ComplaintService complaintService;

    @Before
    public void setUp() throws Exception {
        complaintService.save(new Complaint(1l, "Bad service"));
        complaintService.save(new Complaint(2l, "Unpleasant staff"));
    }

    @Test(expected = ObjectOptimisticLockingFailureException.class)
    public void testOptimistic() throws Throwable {
        ExecutorService executor = Executors.newFixedThreadPool(2);
        Future<?> future1 = executor.submit(new Runnable() {

            @Override
            public void run() {
                System.out.println("Startovan thread 1");
                Complaint complaintToRespond = complaintService.findById(1l);
                complaintToRespond.setReplied(true);
                try { Thread.sleep(3000); } catch (InterruptedException e) {}
                complaintService.save(complaintToRespond);
            }
        });
        executor.submit(new Runnable() {
            @Override
            public void run() {
                System.out.println("Startovan thread 2");
                Complaint complaintToRespond = complaintService.findById(1l);
                complaintToRespond.setReplied(true);
                complaintService.save(complaintToRespond);
            }
        });
        try {
            future1.get();
        } catch (ExecutionException e) {
            System.out.println("Exception from thread " + e.getCause().getClass());
            throw e.getCause();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        executor.shutdown();
    }
}
