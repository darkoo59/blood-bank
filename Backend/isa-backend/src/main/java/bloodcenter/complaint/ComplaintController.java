package bloodcenter.complaint;

import bloodcenter.complaint.dto.ComplaintDTO;
import bloodcenter.complaint.dto.ComplaintResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("api/complaints")
public class ComplaintController {
    @Autowired
    private final ComplaintService complaintService;

    public ComplaintController(ComplaintService complaintService) {
        this.complaintService = complaintService;
    }

    @GetMapping
    public @ResponseBody ArrayList<ComplaintDTO> getAllUnreplied() {
        return complaintService.findAllUnreplied();
    }

    @PatchMapping
    public ResponseEntity<?> respondToComplaint(@RequestBody ComplaintResponseDTO dto) {
        if (complaintService.respondToComplaint(dto)){
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
