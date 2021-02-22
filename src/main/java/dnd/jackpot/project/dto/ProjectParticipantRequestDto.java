package dnd.jackpot.project.dto;

import java.util.List;

import dnd.jackpot.project.entity.ERegion;
import dnd.jackpot.project.entity.Einterest;
import dnd.jackpot.project.entity.Estack;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ProjectParticipantRequestDto {

	private long userIndex;
	private long projectId;
	private long authorId;
	
	@Getter
	@AllArgsConstructor
	public static class RequestResponse {
		private long requestId;
		private long userIndex;
		private long projectId;
		private long authorId;
	}
}
