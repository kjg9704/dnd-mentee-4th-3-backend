package dnd.jackpot.project.entity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import dnd.jackpot.project.dto.CommentDto;
import dnd.jackpot.project.dto.ProjectDto;
import dnd.jackpot.project.dto.ProjectModifyDto;
import dnd.jackpot.project.dto.ProjectSaveDto;
import dnd.jackpot.project.dto.ProjectStackDto;
import dnd.jackpot.user.User;
import dnd.jackpot.user.UserDto;
import dnd.jackpot.user.UserDto.simpleResponse;

public class ProjectMapper {
	
	public static ProjectDto map(Project project, LocalDateTime time, List<String> stack, List<String> position, List<CommentDto.getAll> commentDtos,
			List<UserDto.simpleResponse>participantDtos, List<simpleResponse> requestDtos) {
		ProjectDto pdto = new ProjectDto();
		pdto.setId(project.getId());
		pdto.setTitle(project.getTitle());
		pdto.setShortDesc(project.getShortDesc());
		pdto.setRegion(project.getRegion());
		pdto.setOnline(project.getOnline());
		pdto.setDuration(project.getDuration());
		pdto.setStacks(stack);
		pdto.setPosition(position);
		pdto.setInterest(project.getInterest());
		pdto.setUserIndex(project.getAuthor().getUserIndex());
		pdto.setCreatedDateTime(project.getCreatedAt().toString());
		pdto.setStatus(project.getStatus());
		pdto.setParticipanting(project.getParticipant().size());
		pdto.setUpdatedDateTime(project.getUpdatedAt().toString());
		pdto.setScrapUsers(project.getScrap().size());
		if(Objects.nonNull(participantDtos)) {
			pdto.setParticipants(participantDtos);
		}
		if(Objects.nonNull(commentDtos)) {
			pdto.setComments(commentDtos);
		}
		if(Objects.nonNull(requestDtos)) {
			pdto.setRequests(requestDtos);
		}
		return pdto;
	}
	
	public static Project map(ProjectSaveDto saveDto, User user) {
		ERegion region = ERegion.valueOf(saveDto.getRegion());
		Einterest interest = Einterest.valueOf(saveDto.getInterest());
		return Project.of(saveDto.getShortDesc(), saveDto.getTitle(), region,interest, saveDto.getOnline(),saveDto.getDuration(), user);
	}
	
}
