package dnd.jackpot.project.dto;

import dnd.jackpot.project.entity.Einterest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor
public class ProjectInterestDto {
	private Long id = 0L;
	Einterest interest;
}
