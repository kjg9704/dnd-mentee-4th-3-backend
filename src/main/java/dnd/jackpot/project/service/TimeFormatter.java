package dnd.jackpot.project.service;

import java.time.Duration;
import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

@Service
public class TimeFormatter {
	public String calculateTime(LocalDateTime postedTime) {
		LocalDateTime now = LocalDateTime.now();
		Duration duration= Duration.between(postedTime, now);
		String date = duration.toString();
		return date;
	}

}
