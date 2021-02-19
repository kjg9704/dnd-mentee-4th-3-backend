package dnd.jackpot.project.dao;

import org.springframework.stereotype.Service;

import dnd.jackpot.project.entity.Project;
import dnd.jackpot.project.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProjectDaoImpl implements ProjectDao {
	private final ProjectRepository repo;
	
	@Override
	public Project findById(Long id) {
		return repo.findById(id).orElseThrow();
	}
}
