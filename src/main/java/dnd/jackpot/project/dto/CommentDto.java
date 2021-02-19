package dnd.jackpot.project.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentDto {

	private String body;
	
	private boolean privacy;
	
	private long projectId;
	
}
