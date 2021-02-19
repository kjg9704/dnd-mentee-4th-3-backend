package dnd.jackpot.project.dao;

import dnd.jackpot.project.entity.Project;

public interface ProjectDao {
	public Project findById(Long projectId);
}
