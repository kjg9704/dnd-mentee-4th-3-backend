package dnd.jackpot.project.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dnd.jackpot.project.entity.Project;
import dnd.jackpot.project.entity.ProjectStack;
import dnd.jackpot.stack.entity.EstackProgrammer;
import dnd.jackpot.stack.entity.StackDto;
import lombok.RequiredArgsConstructor;

import dnd.jackpot.project.repository.ProjStackRepo;
import dnd.jackpot.project.repository.ProjectRepository;
//import dnd.jackpot.stack.service.StackService;

@Service
@RequiredArgsConstructor
public class ProjectStackServiceImpl implements ProjectStackService {
	private final ProjStackRepo repo;
	private final ProjectRepository projRepo;
	//private final StackService stackService;
	
//	@Override 
//	@Transactional(readOnly=true)
//	public List<StackDto> getAllByProject(Project project){
//		List<String> stacks = repo.findAllByProject(project);
//		return stacks.stream().map((ProjectStack)->{
//			StackDto dto = new StackDto();
//			dto.setId(Projectstack.getId());
//		}
//	}
	
	
	@Override
	@Transactional
	public List <StackDto> save(List<String> stacks, Project project){
		List<StackDto> dtos = new ArrayList<>();
		for(String stack : stacks) {
			EstackProgrammer stackProgram = EstackProgrammer.valueOf(stack);
			ProjectStack projectStack = ProjectStack.of(project, stackProgram);
			repo.save(projectStack);
			dtos.add(new StackDto(projectStack.getId(),projectStack.getStack()));
		}
		return dtos;
	}

	@Override
	@Transactional
	public void removeByProject(Project project){
		project.getStacks().clear();
		projRepo.save(project);
	}
	
}
