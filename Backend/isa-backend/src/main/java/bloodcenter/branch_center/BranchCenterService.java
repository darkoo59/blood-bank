package bloodcenter.branch_center;

import bloodcenter.address.Address;
import bloodcenter.address.AddressDTO;
import bloodcenter.address.AddressService;
import bloodcenter.available_appointment.AvailableAppointment;
import bloodcenter.available_appointment.AvailableAppointmentService;
import bloodcenter.available_appointment.dto.AvailableAppointmentsDTO;
import bloodcenter.branch_center.dto.*;
import bloodcenter.feedback.Feedback;
import bloodcenter.feedback.dto.FeedbackDTO;
import bloodcenter.person.model.BCAdmin;
import bloodcenter.person.service.BCAdminService;
import bloodcenter.security.filter.AuthUtility;
import bloodcenter.utils.ObjectsMapper;
import bloodcenter.working_days.WorkingDay;

import bloodcenter.working_days.WorkingDayService;
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

import static bloodcenter.utils.ObjectsMapper.convertFeedbackToDTO;

@Service
public class BranchCenterService {
    @Autowired
    private final BranchCenterRepository repository;
    @Autowired
    private final AddressService service;
    private final BCAdminService bcAdminService;
    private final AvailableAppointmentService availableAppointmentService;

    @Autowired
    private final WorkingDayService workingDayService;

    public BranchCenterService(BranchCenterRepository branchCenterRepository, AddressService service, @Lazy BCAdminService
            adminService, @Lazy AvailableAppointmentService availableAppointmentService, WorkingDayService workingDayService) {
        this.repository = branchCenterRepository;
        this.service = service;
        this.bcAdminService = adminService;
        this.availableAppointmentService = availableAppointmentService;
        this.workingDayService = workingDayService;
    }

    public BranchCenter getById(Long id) throws BranchCenter.BCNotFoundException {
        Optional<BranchCenter> center = this.repository.findById(id);
        if (center.isEmpty()) {
            throw new BranchCenter.BCNotFoundException("Branch center not found.");
        }
        return center.get();
    }

    public void registerBranchCenter(RegisterBranchCenterDTO bcDTO) {
        Address address = new Address(bcDTO.address.lat, bcDTO.address.lng, bcDTO.address.street,
                bcDTO.address.number, bcDTO.address.city, bcDTO.address.country);
        service.saveAdress(address);

        WorkingDay workingDay = new WorkingDay(bcDTO.monday, bcDTO.tuesday, bcDTO.wednesday,
                bcDTO.thursday, bcDTO.friday, bcDTO.saturday, bcDTO.sunday);
        workingDayService.saveWorkingDay(workingDay);

        BranchCenter bc = new BranchCenter(bcDTO.name, bcDTO.description, address, bcDTO.getStartTime(), bcDTO.getEndTime(), workingDay);
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

    public SingleBranchCenterDTO getSingleBC(Long id) {
        var bc = repository.findById(id).orElse(null);
        if (bc == null) return null;

        SingleBranchCenterDTO dto = new SingleBranchCenterDTO();
        dto.setName(bc.getName());
        dto.setDescription(bc.getDescription());
        dto.setWorkTime(new WorkingHoursDTO(bc.getStartTime(), bc.getEndTime()));
        dto.setAddress(new AddressDTO(
                bc.getAddress().getId(),
                bc.getAddress().getLat(),
                bc.getAddress().getLng(),
                bc.getAddress().getStreet(),
                bc.getAddress().getNumber(),
                bc.getAddress().getCity(),
                bc.getAddress().getCountry()
        ));
        dto.setWorkingDays(new WorkingDaysDTO(
                bc.getWorkingDays().isMonday(),
                bc.getWorkingDays().isTuesday(),
                bc.getWorkingDays().isSaturday(),
                bc.getWorkingDays().isThursday(),
                bc.getWorkingDays().isFriday(),
                bc.getWorkingDays().isSaturday(),
                bc.getWorkingDays().isSunday()
        ));
        List<FeedbackDTO> feedback = new ArrayList<>();
        for (Feedback f: bc.getFeedback()){
            feedback.add(convertFeedbackToDTO(f));
        }
        dto.setFeedback(feedback);
        var appointments = availableAppointmentService.getByBranchCenterId(bc.getId());
        List<AvailableAppointmentsDTO> availableAppointmentsDTOS = new ArrayList<>();
        for (AvailableAppointment app : appointments) {
            if (app.getStart().isAfter(LocalDateTime.now()))
                availableAppointmentsDTOS.add(ObjectsMapper.convertAvailableAppointmentToDTO(app));
        }
        dto.setAvailableAppointments(availableAppointmentsDTOS);

        return dto;
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

    public ArrayList<BranchCenterDTO> sortBranchCenters(SortRequestDTO request) {
        ArrayList<BranchCenterDTO> list = new ArrayList<>(request.getCentersList());
        String sortBy = request.getSortBy();
        if(sortBy.equals("rating")) {
            boolean ascending = request.isAscending();
            Collections.sort(list, (o1, o2) -> {
                double rating1 = getAverageRating(o1);
                double rating2 = getAverageRating(o2);
                if (ascending) {
                    return Double.compare(rating1, rating2);
                } else {
                    return Double.compare(rating2, rating1);
                }
            });
        }
        return list;
    }

    public float getAverageRating(BranchCenterDTO centerDTO) {
        float averageRating = 0;
        if(centerDTO.getFeedback().size() > 0) {
            for (FeedbackDTO feedback : centerDTO.getFeedback()) {
                averageRating += feedback.getGrade();
            }
            averageRating = averageRating / centerDTO.getFeedback().size();
        }
        return averageRating;
    }
}
