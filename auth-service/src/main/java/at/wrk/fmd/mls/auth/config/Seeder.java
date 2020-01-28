package at.wrk.fmd.mls.auth.config;

import at.wrk.fmd.mls.auth.entity.Concern;
import at.wrk.fmd.mls.auth.entity.Unit;
import at.wrk.fmd.mls.auth.repository.ConcernRepository;
import at.wrk.fmd.mls.auth.repository.UnitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

// TODO This is obviously temporary until units are provided by Coceso

@Component
public class Seeder {

    private final ConcernRepository concernRepository;
    private final UnitRepository unitRepository;

    public Seeder(ConcernRepository concernRepository, UnitRepository unitRepository) {
        this.concernRepository = concernRepository;
        this.unitRepository = unitRepository;
    }

    @Autowired
    @Transactional
    public void createDefaultData() {
        if (concernRepository.count() == 0) {
            Concern concern = new Concern();
            concern.setId(1L);
            concern.setName("Test name");
            concern = concernRepository.save(concern);

            for (long i = 1L; i <= 5L; i++) {
                Unit unit = new Unit();
                unit.setId(i);
                unit.setName("Unit " + i);
                unit.setConcern(concern);
                unitRepository.save(unit);
            }
        }
    }
}
