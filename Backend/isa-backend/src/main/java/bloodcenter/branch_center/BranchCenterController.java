package bloodcenter.branch_center;

import bloodcenter.branch_center.dto.BranchCenterDTO;
import bloodcenter.core.ErrorResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/branch-center")
public class BranchCenterController {
    private final BranchCenterService _service;

    public BranchCenterController(@Autowired BranchCenterService service){
        this._service = service;
    }

    @PatchMapping
    public ResponseEntity<Object> patchBranchCenter(@RequestBody BranchCenterDTO dto) throws BranchCenter.BCNotFoundException {
        //TODO: authorization

        _service.updateData(dto);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ExceptionHandler({ Exception.class })
    public ResponseEntity<Object> handleExceptions(Exception ex){
        ex.printStackTrace();
        return new ResponseEntity<>(new ErrorResponse(ex.getMessage()), HttpStatus.BAD_REQUEST);
    }
}
