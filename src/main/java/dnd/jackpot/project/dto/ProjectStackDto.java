package dnd.jackpot.project.dto;


import dnd.jackpot.project.entity.EstackProgrammer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data @NoArgsConstructor @AllArgsConstructor
public class ProjectStackDto {
	private Long id=0L;
	EstackProgrammer stack;
}
