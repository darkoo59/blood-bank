package bloodcenter.complaint;

import bloodcenter.branch_center.dto.BranchCenterDTO;
import bloodcenter.complaint.dto.ComplaintDTO;
import bloodcenter.complaint.dto.ComplaintResponseDTO;
import bloodcenter.utils.ObjectsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ComplaintService {
    @Autowired
    private final ComplaintRepository complaintRepository;

    public ComplaintService(ComplaintRepository complaintRepository) {this.complaintRepository = complaintRepository; }

    public ArrayList<ComplaintDTO> findAllUnreplied() {
        ArrayList<ComplaintDTO> ret = new ArrayList<ComplaintDTO>();
        for (Complaint complaint : complaintRepository.findAll()) {
            if (!complaint.isReplied()) {
                ret.add(ObjectsMapper.converComplaintToComplaintDTO(complaint));
            }
        }
        return ret;
    }

    public boolean respondToComplaint(ComplaintResponseDTO dto) {
        Complaint complaint = complaintRepository.findById(dto.id).orElse(null);
        if (complaint != null) {
            complaint.setReplied(true);
            complaintRepository.save(complaint);
            System.out.println("Replied to complaint with id: " + dto.id + ". Text is: " + dto.text);
            return true;
        } else {
            return false;
        }
    }

}
