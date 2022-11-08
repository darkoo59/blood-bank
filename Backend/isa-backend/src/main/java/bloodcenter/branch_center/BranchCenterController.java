package bloodcenter.branch_center;

import bloodcenter.branch_center.dto.RegisterBranchCenterDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import bloodcenter.branch_center.dto.BranchCenterDTO;
import bloodcenter.core.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Type;
import java.util.ArrayList;

@RestController
@RequestMapping("api/branch-center")
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


    @GetMapping(path="/all")
    public @ResponseBody ArrayList<BranchCenterDTO> getAll(){ return branchCenterService.findAll(); }

    @PatchMapping
    public ResponseEntity<Object> patchBranchCenter(@RequestBody BranchCenterDTO dto) throws BranchCenter.BCNotFoundException {
        //TODO: authorization

        branchCenterService.updateData(dto);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ExceptionHandler({ Exception.class })
    public ResponseEntity<Object> handleExceptions(Exception ex){
        ex.printStackTrace();
        return new ResponseEntity<>(new ErrorResponse(ex.getMessage()), HttpStatus.BAD_REQUEST);
    }
}
