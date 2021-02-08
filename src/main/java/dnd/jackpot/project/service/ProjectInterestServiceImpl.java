package dnd.jackpot.project.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import dnd.jackpot.project.dto.ProjectInterestDto;
import dnd.jackpot.project.entity.Einterest;
import dnd.jackpot.project.entity.Project;
import dnd.jackpot.project.entity.ProjectInterest;
import dnd.jackpot.project.repository.ProjInterestRepo;
import dnd.jackpot.project.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProjectInterestServiceImpl implements ProjectInterestService  {
	private final ProjInterestRepo repo;
	private final ProjectRepository projRepo;
	
	
	@Override
	@Transactional
	public List <ProjectInterestDto> save(List<String> interests, Project project){
		List<ProjectInterestDto> dtos = new ArrayList<>();
		for(String interest : interests) {
			Einterest einterest = Einterest.valueOf(interest);
			ProjectInterest projectInterest= ProjectInterest.of(project,einterest);
			repo.save(projectInterest);
			dtos.add(new ProjectInterestDto(projectInterest.getId(),projectInterest.getInterest()));
		}
		return dtos;
	}
	
	@Override
	@Transactional
	public void removeByProject(Project project) {
		project.getInterest().clear();
		projRepo.save(project);
	}
}
