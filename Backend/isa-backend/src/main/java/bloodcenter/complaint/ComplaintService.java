package bloodcenter.complaint;

import bloodcenter.complaint.dto.ComplaintDTO;
import bloodcenter.complaint.dto.ComplaintResponseDTO;
import bloodcenter.email.service.EmailService;
import bloodcenter.utils.ObjectsMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class ComplaintService {
    private final ComplaintRepository complaintRepository;
    private final EmailService emailService;

    public ArrayList<ComplaintDTO> findAllUnreplied() {
        ArrayList<ComplaintDTO> ret = new ArrayList<ComplaintDTO>();
        for (Complaint complaint : complaintRepository.findAll()) {
            if (!complaint.isReplied()) {
                ret.add(ObjectsMapper.converComplaintToComplaintDTO(complaint));
            }
        }
        return ret;
    }
    @Transactional
    public boolean respondToComplaint(ComplaintResponseDTO dto) throws MessagingException {
        Complaint complaint = complaintRepository.findById(dto.id).orElse(null);
        if (complaint != null) {
            complaint.setReplied(true);
            complaintRepository.save(complaint);
            System.out.println("Replied to complaint with id: " + dto.id + ". Text is: " + dto.text);
            emailService.send(complaint.getUser().getEmail(), "Complaint response",
                     dto.text);
            return true;
        } else {
            return false;
        }
    }
    @Transactional
    public void save(Complaint complaint) {
        complaintRepository.save(complaint);
    }

    public Complaint findById(Long id) {
        return complaintRepository.findById(id).orElse(null);
    }

}
