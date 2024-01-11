package university.Util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.TreeSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import university.Exception.RequestException;
import university.Model.RegistrationPeriod;
import university.Model.Semester;
import university.Repository.RegistrationPeriodRepo;
@Component
@Getter
@Setter
public class OpeningRegPeriods {
	@Autowired
	private RegistrationPeriodRepo regPeriodRepo;
	private TreeSet<RegistrationPeriod> regPeriods=new TreeSet();
	public RegistrationPeriod getCurrRegPeriod() {
		return regPeriods.last();
	}
	public void validateRegPeriod() {
		RegistrationPeriod latestRegPeriod=null;
		LocalDateTime now=LocalDateTime.now();
		while(!regPeriods.isEmpty()) {
			latestRegPeriod=regPeriods.last();
			if(latestRegPeriod.getCloseTime().isBefore(now))
				regPeriods.pollLast();
			else break;
		}
		if(latestRegPeriod==null) throw new RequestException("The time for registration, adjustment, and review of the course has ended.");
		else if(latestRegPeriod.getOpenTime().isAfter(now)) throw new RequestException("The registration period hasn't been opened. Please visit at "+latestRegPeriod.getOpenTime()+"!");
	}
	public void update() {
		regPeriods.clear();
		LocalDateTime now=LocalDateTime.now();
		for(RegistrationPeriod regPeriod: regPeriodRepo.findAll()) {
			if(!regPeriod.getCloseTime().isBefore(now)) {
				regPeriods.add(regPeriod);
			}
		}
	}
}
