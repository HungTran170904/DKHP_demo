package university.Repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import org.springframework.data.repository.query.Param;
import university.Model.RegistrationPeriod;

public interface RegistrationPeriodRepo extends JpaRepository<RegistrationPeriod, Integer>{
	@Query("select regPeriod from RegistrationPeriod regPeriod order by regPeriod.openTime DESC")
	public List<RegistrationPeriod> getAllRegPeriods();

	@Query("select regPeriod from RegistrationPeriod regPeriod where :now between regPeriod.openTime and regPeriod.closeTime")
	public RegistrationPeriod getCurrRegPeriod(@Param("now") LocalDateTime now);

	@Query("select regPeriod from RegistrationPeriod regPeriod where regPeriod.closeTime>:now order by regPeriod.openTime")
	public RegistrationPeriod getLastPeriod(@Param("now") LocalDateTime now);
}
