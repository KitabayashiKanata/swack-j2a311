package model;

import java.sql.Date;
import java.time.Period;

public class TimeModel {
	public boolean DayChack(Date nowDay, Date day) {
		var period = Period.between(nowDay.toLocalDate(), day.toLocalDate());
		int ye = period.getYears();
		int mo = period.getMonths();
		int da = period.getDays();
		
		if (ye <= -1 || mo <= -1 || da <= -21) {
			return true;
		}
		return false;
	}
}
