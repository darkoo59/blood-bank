package bloodcenter.user.controller;

import bloodcenter.branch_center.dto.BranchCenterDTO;
import bloodcenter.user.model.BCAdmin;
import bloodcenter.user.service.BCAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("api/bc-admin")
public class BCAdminController {
    private final BCAdminService _service;

    public BCAdminController(@Autowired BCAdminService service){
        this._service = service;
    }

    @GetMapping("bc")
    public ResponseEntity<BranchCenterDTO> getBCData(){
        //TODO: get email from token...
        String email = "stojanovicrade614@gmail.com";

        BCAdmin admin = this._service.getByMail(email);
        if (admin == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if (admin.getBranchCenter() == null){
            return new ResponseEntity<>(null, HttpStatus.OK);
        }

        return new ResponseEntity<>(new BranchCenterDTO(admin.getBranchCenter()), HttpStatus.OK);
    }
}
