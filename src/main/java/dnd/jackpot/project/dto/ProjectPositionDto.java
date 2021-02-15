package dnd.jackpot.project.dto;

import dnd.jackpot.project.entity.Eposition;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor
public class ProjectPositionDto {
	private Long id=0L;
	Eposition pos;
}
