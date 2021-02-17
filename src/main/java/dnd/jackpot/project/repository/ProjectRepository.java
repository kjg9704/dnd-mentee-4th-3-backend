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
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import dnd.jackpot.project.entity.ERegion;
import dnd.jackpot.project.entity.Einterest;
import dnd.jackpot.project.entity.EstackProgrammer;
import dnd.jackpot.project.entity.Project;

public interface ProjectRepository extends JpaRepository<Project, Long>, JpaSpecificationExecutor<Project>{
//	프로젝트가 잘 업로드 및 불러오나 보기위해 일단 
	Page<Project> findAll(Pageable pageable);

	@Transactional(readOnly=true)
	@Query("SELECT p FROM Project p "
//			+ "JOIN ProjectStack s "
//			+ "JOIN ProjectInterest i "
			+ "WHERE ((:region) is null OR p.region in :region) "
			+ "AND((:interest) is NULL OR p.interest in :interest)"
			+ "AND((:stack) is NULL OR p.stack in :stack)")
	Page<Project> findByRegionInAndInterestInAndStackIn(@Param("region")List<ERegion> region, 
			@Param("interest")List<Einterest> interest,@Param("stack")List<EstackProgrammer>stack, Pageable pageable);
	
	
//	@Transactional(readOnly = true)
//	@Query("SELECT DISTINCT p FROM Project p WHERE (:region IS NULL OR p.region IN (:region)) AND"
//			+ "(SELECT s FROM ProjectStack s WHERE(:stack IS NULL OR s.stack IN (:stack)) AND "
//			+ "(SELECT i FROM ProjectInterest i WHERE(:interest IS NULL OR i.stack IN (:interest))")
//	Page<Project> findByRegionInAndInterestInAndStackIn(@Param("region")List<ERegion> region, 
//			@Param("interest")List<Einterest> interest,@Param("stack")List<EstackProgrammer>stack, Pageable pageable);
//	//@Param("stack")
////	Page<Project> findByRegionInAndInterestInAndStackIn(List<ERegion>region, List<Einterest>interests, List<EstackProgrammer>stack, Pageable pageable);

	List<Project> findAllByRegionIn(List<ERegion> region);
}

