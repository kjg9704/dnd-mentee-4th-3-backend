package dnd.jackpot.project.dto;

import dnd.jackpot.stack.entity.EstackProgrammer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data @NoArgsConstructor @AllArgsConstructor
public class ProjectStackDto {
	private int id=0;
	EstackProgrammer stack;
}
