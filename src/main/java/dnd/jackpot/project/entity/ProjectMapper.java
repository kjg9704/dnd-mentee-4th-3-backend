package dnd.jackpot.project.entity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import dnd.jackpot.project.dto.ProjectDto;
import dnd.jackpot.project.dto.ProjectModifyDto;
import dnd.jackpot.project.dto.ProjectSaveDto;
import dnd.jackpot.project.dto.ProjectStackDto;
import dnd.jackpot.user.User;

public class ProjectMapper {
	
	public static ProjectDto map(Project project, LocalDateTime time) {//,List<StackDto> stackDtos
		ProjectDto pdto = new ProjectDto();
		pdto.setId(project.getId());
		pdto.setTitle(project.getTitle());
		pdto.setShortDesc(project.getShortDesc());
		pdto.setRegion(project.getRegion());
		pdto.setOnline(project.getOnline());
		pdto.setDuration(project.getDuration());
//		if(Objects.nonNull(stackDtos)){
//			pdto.setStacks(stackDtos);
//		}
//		ProjectStackDto sdto = new ProjectStackDto();
//		pdto.setId(project.getId());
//		pdto.setAuthor(authorDto);
//		pdto.setShortDesc(shortDesc);
//		if(Objects.nonNull(createdDateTime)) {
//			pdto.setCreatedDateTime(createdDateTime.toString());
//		}
		return pdto;
	}
	public static Project map(ProjectSaveDto saveDto) {//User author 추가하기
		ERegion region = ERegion.valueOf(saveDto.getRegion());
		return Project.of(saveDto.getShortDesc(), saveDto.getTitle(), region, saveDto.getOnline(),saveDto.getDuration());//add author later on
	}
	
}
