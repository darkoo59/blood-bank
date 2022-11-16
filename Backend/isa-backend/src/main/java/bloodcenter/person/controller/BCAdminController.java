package bloodcenter.person.controller;

import bloodcenter.branch_center.BranchCenter;
import bloodcenter.branch_center.dto.BranchCenterDTO;
import bloodcenter.core.ErrorResponse;
import bloodcenter.person.dto.BCAdminDTO;
import bloodcenter.person.dto.RegisterBCAdminDTO;
import bloodcenter.person.model.BCAdmin;
import bloodcenter.person.service.BCAdminService;
import bloodcenter.user.dto.AssignAdminToCenterDTO;
import bloodcenter.utils.ObjectsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

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

    @PostMapping
    public ResponseEntity<Object> registerBCAdmin(@RequestBody RegisterBCAdminDTO bcaDTO) throws BCAdmin.BCAdminEmailTakenException {
         service.registerBCAdmin(bcaDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("unassigned")
    public ResponseEntity<ArrayList<BCAdminDTO>> getUnassignedAdmins()  {
        ArrayList<BCAdminDTO> retList = service.getUnassignedAdmins();
        return new ResponseEntity<>(retList, HttpStatus.OK);
    }

    @PatchMapping("assign")
    public ResponseEntity<Object> assignAdminToCenter(@RequestBody AssignAdminToCenterDTO dto) throws BCAdmin.BCAdminNotFoundException, BranchCenter.BCNotFoundException {
        service.assignAdminToCenter(dto);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @ExceptionHandler({ Exception.class })
    public ResponseEntity<Object> handleExceptions(Exception ex){
        ex.printStackTrace();
        return new ResponseEntity<>(new ErrorResponse(ex.getMessage()), HttpStatus.BAD_REQUEST);
    }
}
