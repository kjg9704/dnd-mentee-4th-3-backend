package dnd.jackpot.project.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import dnd.jackpot.project.entity.ERegion;
import dnd.jackpot.project.entity.Einterest;
import dnd.jackpot.project.entity.EprojectStatus;
import dnd.jackpot.user.UserDto;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor
public class ProjectDto {
	private Long id = 0L;
	private String shortDesc="";
	private List<String> stacks = new ArrayList<>();
	private Einterest interest;
	private List<String> position = new ArrayList<>();
	private String title = "";
	private String createdDateTime;
	private ERegion region;
	private String recruitmentPeriod;
	private String online;
	private EprojectStatus status;
	private String duration;
	private long userIndex;
	private int scrapUsers;
	private int participanting;
	private String updatedDateTime;
	private List<UserDto.simpleResponse> participants = new ArrayList<>();
	private List<CommentDto.getAll> comments = new ArrayList<>();
	private List<UserDto.simpleResponse> requests = new ArrayList<>();

	@Data
	public static class filterDto{
		private Long id;
		private String title;
		private List<String> stacks = new ArrayList();
		private List<String> position = new ArrayList();
		private String interest;
		private int scrapped;
	}
}
