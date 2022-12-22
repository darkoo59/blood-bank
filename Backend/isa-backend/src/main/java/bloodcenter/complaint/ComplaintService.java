package bloodcenter.complaint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ComplaintService {
    @Autowired
    private final ComplaintRepository complaintRepository;


    public ComplaintService(ComplaintRepository complaintRepository) {this.complaintRepository = complaintRepository; }
}
