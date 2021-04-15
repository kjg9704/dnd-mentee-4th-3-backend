package dnd.jackpot.project.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dnd.jackpot.project.dto.ProjectStackDto;
import dnd.jackpot.project.entity.Estack;
import dnd.jackpot.project.entity.Project;

import dnd.jackpot.project.entity.ProjectStack;


import lombok.RequiredArgsConstructor;

import dnd.jackpot.project.repository.ProjStackRepo;
import dnd.jackpot.project.repository.ProjectRepository;


@Service
@RequiredArgsConstructor
public class ProjectStackServiceImpl implements ProjectStackService {
	private final ProjStackRepo repo;
	private final ProjectRepository projRepo;
	
	@Override 
	@Transactional(readOnly=true)
	public List<String> getAllByProject(Project project){
		List<ProjectStack> stacks = repo.findAllByProject(project);
		List<String>Sstack = new ArrayList<>();
		for(ProjectStack stack: stacks) {
			Sstack.add(stack.getStack().toString());
		}
		return Sstack;
	}
	


	
	
	@Override
	@Transactional
	public List <ProjectStackDto> save(List<String> stacks, Project project){
		List<ProjectStackDto> dtos = new ArrayList<>();
		for(String stack : stacks) {
			Estack stackProgram = Estack.valueOf(stack);
			ProjectStack projectStack = ProjectStack.of(project, stackProgram);
			repo.save(projectStack);
			dtos.add(new ProjectStackDto(projectStack.getId(),projectStack.getStack()));
		}
		return dtos;
	}

	@Override
	@Transactional
	public void removeByProject(Project project){
		project.getStack().clear();
		projRepo.save(project);
	}
	
}
