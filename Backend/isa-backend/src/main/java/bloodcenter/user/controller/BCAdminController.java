package bloodcenter.user.controller;

import bloodcenter.branch_center.dto.BranchCenterDTO;
import bloodcenter.core.ErrorResponse;
import bloodcenter.user.dto.RegisterBCAdminDTO;
import bloodcenter.user.model.BCAdmin;
import bloodcenter.user.service.BCAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("api/bc-admin")
public class BCAdminController {
    private final BCAdminService _service;

    public BCAdminController(@Autowired BCAdminService service){
        this._service = service;
    }

    @GetMapping("bc")
    public ResponseEntity<BranchCenterDTO> getBCData() throws BCAdmin.BCAdminNotFoundException {
        //TODO: get email from token...
        String email = "stojanovicrade614@gmail.com";

        BCAdmin admin = this._service.getByMail(email);

        if (admin.getBranchCenter() == null){
            return new ResponseEntity<>(null, HttpStatus.OK);
        }
        return new ResponseEntity<>(new BranchCenterDTO(admin.getBranchCenter()), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Object> registerBCAdmin(@RequestBody RegisterBCAdminDTO bcaDTO) {
        _service.registerBCAdmin(bcaDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @ExceptionHandler({ Exception.class })
    public ResponseEntity<Object> handleExceptions(Exception ex){
        ex.printStackTrace();
        return new ResponseEntity<>(new ErrorResponse(ex.getMessage()), HttpStatus.BAD_REQUEST);
    }
}
