package bloodcenter.branch_center;

import bloodcenter.branch_center.dto.RegisterBranchCenterDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/center")
public class BranchCenterController {

    private final BranchCenterService branchCenterService;

    @Autowired
    public BranchCenterController(BranchCenterService branchCenterService) {
        this.branchCenterService = branchCenterService;
    }
    @PostMapping
    public void registerBranchCenter(@RequestBody RegisterBranchCenterDTO bcDTO) {
        branchCenterService.registerBranchCenter(bcDTO);
    }





}
