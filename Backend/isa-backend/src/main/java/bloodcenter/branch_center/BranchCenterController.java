package bloodcenter.branch_center;

import bloodcenter.branch_center.dto.RegisterBranchCenterDTO;
import bloodcenter.branch_center.dto.SingleBranchCenterDTO;
import bloodcenter.branch_center.dto.SortRequestDTO;
import bloodcenter.branch_center.dto.WorkingHoursDTO;
import bloodcenter.person.model.BCAdmin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import bloodcenter.branch_center.dto.BranchCenterDTO;
import bloodcenter.core.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/branch-center")
public class BranchCenterController {

    private final BranchCenterService service;

    @Autowired
    public BranchCenterController(BranchCenterService service) {
        this.service = service;
    }
    @PostMapping
    @Secured({"ROLE_ADMIN"})
    public void registerBranchCenter(@RequestBody RegisterBranchCenterDTO bcDTO) {
        service.registerBranchCenter(bcDTO);
    }

    @GetMapping(path="/all")
    public @ResponseBody ArrayList<BranchCenterDTO> getAll(){ return service.findAll(); }

    @Secured({"ROLE_USER"})
    @GetMapping(path="/view/{id}")
    public ResponseEntity<SingleBranchCenterDTO> getSingleBC(@PathVariable("id") Long id) {
        return new ResponseEntity<>(service.getSingleBC(id), HttpStatus.OK);
    }

    @GetMapping(path="/all-centers-pagination")
    public @ResponseBody ResponseEntity<Map<String,Object>> getAllPages(
            @RequestParam(required = false) String name,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "3") int size,
            @RequestParam(value="country", required = false) String country,
            @RequestParam(value="city", required = false) String city,
            @RequestParam(value="order", required = false) String order
    ) {
        return new ResponseEntity<>(service.findAllPagesFiltered(name,page,size,country,city,order), HttpStatus.OK);
    }

    @Secured({"ROLE_BCADMIN"})
    @PatchMapping
    public ResponseEntity<Object> patchBranchCenter(@RequestBody BranchCenterDTO dto) throws BranchCenter.BCNotFoundException {
        service.updateData(dto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Secured({"ROLE_BCADMIN"})
    @GetMapping(path="/working-hours")
    public WorkingHoursDTO getWorkingHours(HttpServletRequest request) throws BCAdmin.BCAdminNotFoundException {
        return service.getWorkingHours(request);
    }

    @Secured({"ROLE_BCADMIN"})
    @GetMapping(path="/working-days")
    public List<Integer> getWorkingDays(HttpServletRequest request) throws BCAdmin.BCAdminNotFoundException {
        return service.getWorkingDays(request);
    }

    @GetMapping(path="/allCountries")
    public @ResponseBody List<String> getAllCountries(){ return service.getAllCountriesForFiltering(); }

    @GetMapping(path="/allCities")
    public @ResponseBody List<String> getAllCities(){ return service.getAllCitiesForFiltering(); }

    @Secured({"ROLE_USER"})
    @GetMapping(path="/available-for-appointment-date")
    public @ResponseBody ArrayList<BranchCenterDTO> getAvailableForAppointmentDate
            (@RequestParam String date){ return service.findAvailableForAppointmentDate(date); }

    @Secured({"ROLE_USER"})
    @PostMapping(path="/sorted")
    public @ResponseBody ArrayList<BranchCenterDTO> getSorted
            (@RequestBody SortRequestDTO request){ return service.sortBranchCenters(request); }


    @ExceptionHandler({ Exception.class })
    public ResponseEntity<Object> handleExceptions(Exception ex){
        ex.printStackTrace();
        return new ResponseEntity<>(new ErrorResponse(ex.getMessage()), HttpStatus.BAD_REQUEST);
    }
}
