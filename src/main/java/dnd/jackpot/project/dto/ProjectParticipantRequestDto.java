package dnd.jackpot.project.dto;

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
}
