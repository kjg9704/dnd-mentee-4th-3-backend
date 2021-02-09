package dnd.jackpot.filter;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import dnd.jackpot.project.dto.ProjectDto;

public class PFilterServiceImpl implements PFilterService {
	
	@Override 
	public List<PFilterDto> getAll(PFilterSearchDto searchDto){
		if(Objects.nonNull(searchDto.getRegionFilter())) {
			List<ProjectDto> projectDtoList = ProjectMapperService.toDto(pageProjects.getContent());
		}
		if(Objects.nonNull(searchDto.getStackFilter())) {
			List<ProjectDto> projectDtoList = ProjectMapperService.toDto(pageProjects.getContent());
		}
		if(Objects.nonNull(searchDto.getInterestFilter())) {
			List<ProjectDto> projectDtoList = ProjectMapperService.toDto(pageProjects.getContent());
		}
		for()
		
	}
}
