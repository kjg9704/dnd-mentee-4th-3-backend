package dnd.jackpot.project.entity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import dnd.jackpot.project.dto.ProjectDto;
import dnd.jackpot.project.dto.ProjectSaveDto;
import dnd.jackpot.stack.entity.StackDto;
import dnd.jackpot.user.User;

public class ProjectMapper {
	
	public static ProjectDto map(Project project, LocalDateTime time) {//,List<StackDto> stackDtos
		ProjectDto pdto = new ProjectDto();
//		if(Objects.nonNull(stackDtos)){
//			pdto.setStacks(stackDtos);
//		}
		StackDto sdto = new StackDto();
//		pdto.setId(project.getId());
//		pdto.setAuthor(authorDto);
//		pdto.setShortDesc(shortDesc);
//		if(Objects.nonNull(createdDateTime)) {
//			pdto.setCreatedDateTime(createdDateTime.toString());
//		}
		return pdto;
	}
	public static Project map(ProjectSaveDto saveDto) {//User author 추가하기
		return Project.of(saveDto.getShortDesc(), saveDto.getTitle(), saveDto.getRegion());//add author later on
	}
//	public static Project map(ProjectSaveDto saveDto, User author) {
//		// TODO Auto-generated method stub
//		return null;
//	}
	
}
