package dnd.jackpot.project.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import dnd.jackpot.project.entity.Project;

public interface ProjectRepository extends JpaRepository<Project, Integer>{
//	프로젝트가 잘 업로드 및 불러오나 보기위해 일단 
	Page<Project> findAll(Pageable pageable);
}
