package dnd.jackpot.project.entity;

import java.time.LocalDateTime;
import java.util.Objects;

import dnd.jackpot.project.dto.ProjectDto;
import dnd.jackpot.project.dto.ProjectSaveDto;
import dnd.jackpot.user.User;

public class ProjectMapper {
	
	public static ProjectDto map(Project project, LocalDateTime time) {
		ProjectDto pdto = new ProjectDto();
//		pdto.setId(project.getId());
//		pdto.setAuthor(authorDto);
//		pdto.setShortDesc(shortDesc);
//		if(Objects.nonNull(createdDateTime)) {
//			pdto.setCreatedDateTime(createdDateTime.toString());
//		}
		return pdto;
	}
	public static Project map(ProjectSaveDto saveDto) {//User author 추가하기
		return Project.of(saveDto.getShortDesc());//add author later on
	}
//	public static Project map(ProjectSaveDto saveDto, User author) {
//		// TODO Auto-generated method stub
//		return null;
//	}
	
}
