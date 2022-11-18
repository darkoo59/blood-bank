package bloodcenter.person.controller;

import bloodcenter.branch_center.BranchCenter;
import bloodcenter.branch_center.dto.BranchCenterDTO;
import bloodcenter.core.ErrorResponse;
import bloodcenter.person.dto.BCAdminDTO;
import bloodcenter.person.dto.RegisterBCAdminDTO;
import bloodcenter.person.model.BCAdmin;
import bloodcenter.person.service.BCAdminService;
import bloodcenter.person.dto.AssignAdminToCenterDTO;
import bloodcenter.person.service.PersonService;
import bloodcenter.security.filter.AuthUtility;
import bloodcenter.utils.ObjectsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;

@Controller
@RequestMapping("api/bc-admin")
public class BCAdminController {
    private final BCAdminService service;

    private final AuthUtility authUtility;

    public BCAdminController(@Autowired BCAdminService service, PersonService personService){
        this.service = service;
        this.authUtility = new AuthUtility(personService);
    }

    @GetMapping("bc")
    @Secured({"ROLE_BCADMIN"})
    public ResponseEntity<BranchCenterDTO> getBCData(HttpServletRequest request) throws BCAdmin.BCAdminNotFoundException {
        String email = authUtility.getEmailFromRequest(request);
        BCAdmin admin = this.service.getByMail(email);

        if (admin.getBranchCenter() == null)
            return new ResponseEntity<>(null, HttpStatus.OK);
        BranchCenterDTO dto = ObjectsMapper.convertBranchCenterToDTO(admin.getBranchCenter());
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @PatchMapping("bc")
    @Secured({"ROLE_BCADMIN"})
    public ResponseEntity<Object> patchBranchCenter(HttpServletRequest request, @RequestBody BranchCenterDTO dto) throws Exception {
        String email = authUtility.getEmailFromRequest(request);
        service.updateBranchCenter(email, dto);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @PostMapping
    @Secured({"ROLE_ADMIN"})
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
    @Secured({"ROLE_ADMIN"})
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
