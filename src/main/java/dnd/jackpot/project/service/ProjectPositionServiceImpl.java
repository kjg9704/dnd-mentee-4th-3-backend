package dnd.jackpot.project.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import org.springframework.stereotype.Service;

import dnd.jackpot.project.dto.ProjectPositionDto;
import dnd.jackpot.project.entity.Eposition;
import dnd.jackpot.project.entity.Project;
import dnd.jackpot.project.entity.ProjectInterest;
import dnd.jackpot.project.entity.ProjectPosition;
import dnd.jackpot.project.repository.ProjectPositionRepo;
import dnd.jackpot.project.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProjectPositionServiceImpl implements ProjectPositionService{
	private final ProjectPositionRepo repo;
	private final ProjectRepository projRepo;
	@Override
	@Transactional
	public List<ProjectPositionDto> save(List<String> positions, Project project){
		List<ProjectPositionDto> dtos = new ArrayList<>();
		for(String pos : positions) {
			Eposition position = Eposition.valueOf(pos);
			ProjectPosition projectPosition = ProjectPosition.of(project, position);
			repo.save(projectPosition);
			dtos.add(new ProjectPositionDto(projectPosition.getId(), projectPosition.getPosition()));
		}
		return dtos;
	}
	

	@Override 
	@Transactional(readOnly=true)
	public List<String> getAllByProject(Project project){
		List<ProjectPosition> positions = repo.findAllByProject(project);
		List<String>Sinterest = new ArrayList<>();
		for(ProjectPosition position : positions) {
			Sinterest.add(position.getPosition().toString());
		}
		return Sinterest;
	}
	
	@Override
	@Transactional
	public void removeByProject(Project project) {
		project.getPosition().clear();
		projRepo.save(project);
	}
	
}
