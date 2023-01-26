package bloodcenter.working_days;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WorkingDayService {

    private final WorkingDaysRepository _repository;

    @Autowired
    public WorkingDayService(WorkingDaysRepository repository) { this._repository = repository; }

    public void saveWorkingDay(WorkingDay workingDay) { _repository.save(workingDay); }
}
