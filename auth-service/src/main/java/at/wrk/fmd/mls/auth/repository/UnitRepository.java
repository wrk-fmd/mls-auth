package at.wrk.fmd.mls.auth.repository;

import at.wrk.fmd.mls.auth.entity.Concern;
import at.wrk.fmd.mls.auth.entity.Unit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface UnitRepository extends JpaRepository<Unit, Long> {

    Collection<Unit> findByConcern(Concern concern);
}
