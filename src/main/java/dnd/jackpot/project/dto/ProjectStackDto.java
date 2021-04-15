package dnd.jackpot.project.dto;


import dnd.jackpot.project.entity.Estack;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data @NoArgsConstructor @AllArgsConstructor
public class ProjectStackDto {
	private Long id=0L;
	Estack stack;
}
