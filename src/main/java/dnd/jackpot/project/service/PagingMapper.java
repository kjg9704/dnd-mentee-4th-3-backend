package dnd.jackpot.project.service;

import java.util.List;

import org.springframework.data.domain.Page;

import dnd.jackpot.project.dto.PagingDto;
import dnd.jackpot.project.dto.ProjectDto;
import dnd.jackpot.project.dto.ProjectDto.filterDto;
import dnd.jackpot.project.entity.Project;
import dnd.jackpot.user.User;
import dnd.jackpot.user.UserDto.simpleResponse;

public class PagingMapper {
	public static PagingDto<filterDto> map(Page<Project> pageProjects, List<filterDto> filterDtos){
		PagingDto<filterDto> dto = new PagingDto<>();
		dto.setPageNumber(pageProjects.getNumber());
		dto.setPageSize(pageProjects.getSize());
		dto.setTotalPages(pageProjects.getTotalPages());
		dto.setContents(filterDtos);
		//last,first,content 나중에 추가
		return dto;
	}
	
	public static PagingDto<simpleResponse> mapUser(Page<User> pageProjects, List<simpleResponse> projectDtos){
		PagingDto<simpleResponse> dto = new PagingDto<>();
		dto.setPageNumber(pageProjects.getNumber());
		dto.setPageSize(pageProjects.getSize());
		dto.setTotalPages(pageProjects.getTotalPages());
		dto.setContents(projectDtos);
		//last,first,content 나중에 추가
		return dto;
	}
}
