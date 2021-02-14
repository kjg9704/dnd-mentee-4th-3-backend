package dnd.jackpot.project.repository;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import dnd.jackpot.project.entity.ERegion;
import dnd.jackpot.project.entity.Einterest;
import dnd.jackpot.project.entity.EstackProgrammer;
import dnd.jackpot.project.entity.Project;

public interface ProjectRepository extends JpaRepository<Project, Long>, JpaSpecificationExecutor<Project>{
//	프로젝트가 잘 업로드 및 불러오나 보기위해 일단 
	Page<Project> findAll(Pageable pageable);

//	@Query("SELECT p FROM project p WHERE (:region is null or p.region=region) and"
//			+ "(:stack is null or p.stack = stakc) and "
//			+ "(:interest is null or p.interest = interest)")
//	List<Project> findByRegionInAndColumnInAndStackIn(@Param("region")List<ERegion> regions, 
//			@Param("stack")List<EstackProgrammer>stack, @Param("interest") List<Einterest> interest);
	
	Page<Project> findByRegionInAndInterestInAndStacksIn(List<ERegion>region, List<Einterest>interests, List<EstackProgrammer>stacks, Pageable pageable);


}

