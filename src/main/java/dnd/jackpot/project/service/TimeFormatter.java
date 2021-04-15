package dnd.jackpot.project.service;

import java.time.Duration;
import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

@Service
public class TimeFormatter {
	public String calculateTime(LocalDateTime postedTime) {
		LocalDateTime now = LocalDateTime.now();
		Duration duration= Duration.between(postedTime, now);
		String day="";
		if(duration.toDays() > 30) {
			long M = duration.toDays()/30;
			day = Long.toString(M).concat("Month");
			return day;
		}
		if(duration.toDays()!=0) {
			day = Long.toString(duration.toDays()).concat("D");
			return day;
		}
		if(duration.toHours()!=0) {
			day = Long.toString(duration.toHours()).concat("H");
			return day;
		}
		if(duration.toMinutes()!=0) {
			day = Long.toString(duration.toMinutes()).concat("Min");
			return day;
		}
		if(duration.toSeconds()!=0) {
			day = Long.toString(duration.toSeconds()).concat("S");
			return day;
		}
		return day;
	}

}
