package bloodcenter.user.controller;

import bloodcenter.branch_center.dto.BranchCenterDTO;
import bloodcenter.core.ErrorResponse;
import bloodcenter.user.model.BCAdmin;
import bloodcenter.user.service.BCAdminService;
import bloodcenter.utils.ObjectsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("api/bc-admin")
public class BCAdminController {
    private final BCAdminService service;

    public BCAdminController(@Autowired BCAdminService service){
        this.service = service;
    }

    @GetMapping("bc")
    public ResponseEntity<BranchCenterDTO> getBCData() throws BCAdmin.BCAdminNotFoundException {
        //TODO: get email from token...
        String email = "stojanovicrade614@gmail.com";

        BCAdmin admin = this.service.getByMail(email);

        if (admin.getBranchCenter() == null){
            return new ResponseEntity<>(null, HttpStatus.OK);
        }
        BranchCenterDTO dto = ObjectsMapper.convertBranchCenterToDTO(admin.getBranchCenter());
        dto.address = ObjectsMapper.convertAddressToDTO(admin.getBranchCenter().getAddress());
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }


    @ExceptionHandler({ Exception.class })
    public ResponseEntity<Object> handleExceptions(Exception ex){
        ex.printStackTrace();
        return new ResponseEntity<>(new ErrorResponse(ex.getMessage()), HttpStatus.BAD_REQUEST);
    }
}
