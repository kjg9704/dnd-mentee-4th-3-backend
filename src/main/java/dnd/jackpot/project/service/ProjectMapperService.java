package dnd.jackpot.project.service;

import java.util.List;

import org.springframework.stereotype.Component;

import dnd.jackpot.project.dto.ProjectDto;
import dnd.jackpot.project.dto.ProjectDto.filterDto;
import dnd.jackpot.project.entity.Project;

public interface ProjectMapperService {

	filterDto toDto(Project project);
	List<filterDto> toDto(List<Project>project);
}
