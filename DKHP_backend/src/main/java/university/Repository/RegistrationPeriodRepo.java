package university.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import university.Model.RegistrationPeriod;

public interface RegistrationPeriodRepo extends JpaRepository<RegistrationPeriod, Integer>{
	@Query("select regPeriod from RegistrationPeriod regPeriod order by regPeriod.openTime DESC")
	public List<RegistrationPeriod> getAllRegPeriods();
}
