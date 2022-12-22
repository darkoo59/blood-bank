package bloodcenter.branch_center;

import bloodcenter.address.Address;
import bloodcenter.address.AddressService;
import bloodcenter.available_appointment.AvailableAppointment;
import bloodcenter.branch_center.dto.RegisterBranchCenterDTO;
import bloodcenter.branch_center.dto.BranchCenterDTO;
import bloodcenter.branch_center.dto.WorkingHoursDTO;
import bloodcenter.person.model.BCAdmin;
import bloodcenter.person.service.BCAdminService;
import bloodcenter.security.filter.AuthUtility;
import bloodcenter.utils.ObjectsMapper;
import bloodcenter.working_days.WorkingDay;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Service
public class BranchCenterService {
    @Autowired
    private final BranchCenterRepository repository;
    @Autowired
    private final AddressService service;
    private final BCAdminService bcAdminService;

    public BranchCenterService(BranchCenterRepository branchCenterRepository, AddressService service, @Lazy BCAdminService
                               adminService) {
        this.repository = branchCenterRepository;
        this.service = service;
        this.bcAdminService = adminService;
    }

    public BranchCenter getById(Long id) throws BranchCenter.BCNotFoundException {
        Optional<BranchCenter> center = this.repository.findById(id);
        if (center.isEmpty()) {
            throw new BranchCenter.BCNotFoundException("Branch center not found.");
        }
        BranchCenter ret = center.get();
        return ret;
    }

    public void registerBranchCenter(RegisterBranchCenterDTO bcDTO) {
        Address address = new Address(bcDTO.address.lat, bcDTO.address.lng, bcDTO.address.street,
                bcDTO.address.number, bcDTO.address.city, bcDTO.address.country);
        service.saveAdress(address);
        BranchCenter bc = new BranchCenter(bcDTO.name, bcDTO.description, address);
        repository.save(bc);
    }


    public ArrayList<BranchCenterDTO> findAll(){
        ArrayList<BranchCenterDTO> centersToReturn = new ArrayList<BranchCenterDTO>();
        for (BranchCenter center: repository.findAll()) {
            centersToReturn.add(ObjectsMapper.convertBranchCenterToDTO(center));
        }
        return centersToReturn;
    }

    public Map<String, Object> findAllPagesFiltered(String name, int page, int size,String country, String city) {
        List<BranchCenterDTO> centers = new ArrayList<>();
        Pageable paging = PageRequest.of(page, size);

        Page<BranchCenter> pageCenter;
        ArrayList<BranchCenterDTO> centersToReturn = new ArrayList<>();
        if (name == null && country == null && city == null)
            pageCenter = repository.findAll(paging);
        else
            pageCenter = repository.findSearched(name,country,city, paging);

        for (BranchCenter center:pageCenter.getContent()) {
            centers.add(ObjectsMapper.convertBranchCenterToDTO(center));
        }

        Map<String, Object> response = new HashMap<>();
        response.put("branchCenters", centers);
        response.put("currentPage", pageCenter.getNumber());
        response.put("totalItems", pageCenter.getTotalElements());
        response.put("totalPages", pageCenter.getTotalPages());

        return response;
    }

    public void updateData(BranchCenterDTO dto) throws BranchCenter.BCNotFoundException {
        Optional<BranchCenter> o_bc = repository.findById(dto.id);
        if(o_bc.isEmpty())
            throw new BranchCenter.BCNotFoundException("Branch center not found");

        BranchCenter bc = o_bc.get();
        Address a = bc.getAddress();
        a.setCity(dto.address.getCity());
        a.setStreet(dto.address.getStreet());
        a.setNumber(dto.address.getNumber());
        a.setCountry(dto.address.getCountry());
        a.setLat(dto.address.getLat());
        a.setLng(dto.address.getLng());

        bc.setName(dto.name);
        bc.setDescription(dto.description);

        repository.save(bc);
    }

    public List<String> getAllCountriesForFiltering(){ return service.getAllCountries();}

    public List<String> getAllCitiesForFiltering(){ return service.getAllCities();}

    public WorkingHoursDTO getWorkingHours(HttpServletRequest request) throws BCAdmin.BCAdminNotFoundException {
        String adminEmail = AuthUtility.getEmailFromRequest(request);
        BranchCenter center = bcAdminService.getBranchCenterByAdminEmail(adminEmail);
        WorkingHoursDTO workingHours = new WorkingHoursDTO();
        workingHours.setStartTime(center.getStartTime());
        workingHours.setEndTime(center.getEndTime());
        return workingHours;
    }

    public List<Integer> getWorkingDays(HttpServletRequest request) throws BCAdmin.BCAdminNotFoundException {
        String adminEmail = AuthUtility.getEmailFromRequest(request);
        BranchCenter center = bcAdminService.getBranchCenterByAdminEmail(adminEmail);
        WorkingDay days = center.getWorkingDays();
        return days.generateIntegerList();
    }

    public ArrayList<BranchCenterDTO> findAvailableForAppointmentDate(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        LocalDateTime dateTime = LocalDateTime.parse(date, formatter);
        ArrayList<BranchCenterDTO> centersDto = new ArrayList<>();
        for (BranchCenter bc: repository.findAll()) {
            for(AvailableAppointment availableAppointment : bc.getAvailableAppointments()){
                if((availableAppointment.getStart().isBefore(dateTime) || availableAppointment.getStart().isEqual(dateTime))
                        && availableAppointment.getEnd().isAfter(dateTime)) {
                    centersDto.add(ObjectsMapper.convertBranchCenterToDTO(bc));
                    break;
                }
            }
        }
        return centersDto;
    }
}
