package dnd.jackpot.filter;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;

import dnd.jackpot.project.dto.ProjectDto;
import dnd.jackpot.project.entity.Project;
import dnd.jackpot.project.repository.ProjectRepository;

public class PFilterServiceImpl implements PFilterService {
	private final ProjectRepository repo;
	
	private List<Project> RprojectList;
	private List<Project> SprojectList;
	private List<Project> IprojectList;
	
	@Override 
	public List<PFilterDto> getAll(PFilterSearchDto searchDto){
		Page<Project> pageProjects;
		if(Objects.nonNull(searchDto.getRegionFilter())) {
//			 = ProjectMapperService.toDto(pageProjects.getContent());
//			repo.findAll()->
			RprojectList = repo.findByInterest(String a);
		}else {
			RprojectList = repo.findAll();
//			필터를 적용하지 않는경우 다 가져온다.
//			RprojectDtoList = get
		}
		if(Objects.nonNull(searchDto.getStackFilter())) {
//			 = ProjectMapperService.toDto(pageProjects.getContent());
		}else {
			SprojectList = repo.findAll();
		}
		if(Objects.nonNull(searchDto.getInterestFilter())) {
			
		}else {
			IprojectList = repo.findAll();
		}
		if(!RprojectDtoList.isEmpty() && )
		
		
		
	}
}
