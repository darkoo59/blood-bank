package bloodcenter.subscribed_hospitals.service;

import bloodcenter.address.AddressService;
import bloodcenter.branch_center.BranchCenterRepository;
import bloodcenter.feedback.FeedbackRepository;
import bloodcenter.subscribed_hospitals.dto.HospitalDTO;
import bloodcenter.subscribed_hospitals.model.SubscribedHospital;
import bloodcenter.subscribed_hospitals.repository.SubscribedHospitalRepository;
import bloodcenter.utils.ObjectsMapper;
import org.springframework.stereotype.Service;

@Service
public class SubscribedHospitalService {
    private final SubscribedHospitalRepository subscribedHospitalRepository;

    public SubscribedHospitalService(SubscribedHospitalRepository repository) {
        this.subscribedHospitalRepository = repository;
    }

    public void subscribe(HospitalDTO hospitalDTO){
        SubscribedHospital hospitalToSubscribe = ObjectsMapper.convertHospitalDTOToSubscribedHospital(hospitalDTO);
        subscribedHospitalRepository.save(hospitalToSubscribe);
    }
}
