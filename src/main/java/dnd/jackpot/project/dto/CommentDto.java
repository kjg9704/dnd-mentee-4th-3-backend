package dnd.jackpot.project.dto;

import javax.validation.constraints.NotBlank;

import dnd.jackpot.project.entity.Project;
import dnd.jackpot.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class CommentDto {
	@Data
	public static class save{
		private String body;
		private boolean privacy;
		
		@NotBlank 
		private long projectId;
	}
	
	@Data @AllArgsConstructor
	@NoArgsConstructor
	public static class getAll{
		private long id;
		private String body;
		private String date;
		private String authorName;
		private String authorPosition;
		
	}
	
	
	
}
