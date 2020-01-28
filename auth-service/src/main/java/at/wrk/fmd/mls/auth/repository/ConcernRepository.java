package at.wrk.fmd.mls.auth.repository;

import at.wrk.fmd.mls.auth.entity.Concern;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConcernRepository extends JpaRepository<Concern, Long> {

}
