package bloodcenter.branch_center;

import bloodcenter.branch_center.dto.BranchCenterDTO;
import bloodcenter.utils.ObjectsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class BranchCenterService {
    private final BranchCenterRepository _repository;

    public BranchCenterService(@Autowired BranchCenterRepository repo){
        this._repository = repo;
    }

    public ArrayList<BranchCenterDTO> findAll(){
        ArrayList<BranchCenterDTO> centersToReturn = new ArrayList<BranchCenterDTO>();
        for (BranchCenter center: _repository.findAll()) {
            centersToReturn.add(ObjectsMapper.convertBranchCenterToDTO(center));
        }
        return centersToReturn;
    }

    public Map<String, Object> findAllPagesByName(String name, int page, int size) {
        List<BranchCenterDTO> centers = new ArrayList<>();
        Pageable paging = PageRequest.of(page, size);

        Page<BranchCenter> pageCenter;
        ArrayList<BranchCenterDTO> centersToReturn = new ArrayList<>();
        if (name == null)
            pageCenter = _repository.findAll(paging);
        else
            pageCenter = _repository.findByNameContaining(name, paging);

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
        Optional<BranchCenter> o_bc = _repository.findById(dto.id);
        if(o_bc.isEmpty()){
            throw new BranchCenter.BCNotFoundException("Branch center not found");
        }
        BranchCenter bc = o_bc.get();
        bc.setAddress(dto.address);
        bc.setName(dto.name);
        bc.setDescription(dto.description);

        _repository.save(bc);
    }
}
