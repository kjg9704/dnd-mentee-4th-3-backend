package dnd.jackpot.project.repository;

import java.util.List;



import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import dnd.jackpot.project.entity.ERegion;
import dnd.jackpot.project.entity.Einterest;
import dnd.jackpot.project.entity.Estack;
import dnd.jackpot.project.entity.Project;
import dnd.jackpot.user.User;

public interface ProjectRepository extends JpaRepository<Project, Long>, JpaSpecificationExecutor<Project>{
//	프로젝트가 잘 업로드 및 불러오나 보기위해 일단 
	Page<Project> findAll(Pageable pageable);

//	@Transactional(readOnly=true)
	@Query(value ="SELECT p FROM Project p "
			+ "JOIN p.stack s "
			+ "WHERE ((:region is null) OR (p.region = :region)) "
			+ "AND ((:interest is null) OR (p.interest in :interest)) "
			+ "AND ((:stack is null) OR (s.stack in :stack))"
			+ "AND ((:duration is null) OR (p.duration in :duration)) ORDER BY p.scrap.size DESC")
	Page<Project> findByRegionInAndInterestInAndStackInORDERBYpopular(@Param("region")ERegion rprojectList, 
			@Param("interest")List<Einterest> interest, @Param("stack")List<Estack>stack, 
			@Param("duration")List<String> dprojectList, Pageable pageable);

	@Query(value ="SELECT p FROM Project p "
			+ "JOIN p.stack s "
			+ "WHERE ((:region is null) OR (p.region in :region)) "
			+ "AND ((:interest is null) OR (p.interest in :interest)) "
			+ "AND ((:stack is null) OR (s.stack in :stack))"
			+ "AND ((:duration is null) OR (p.duration in :duration)) ORDER BY p.createdAt DESC")
	Page<Project> findByRegionInAndInterestInAndStackInORDERBYdate(@Param("region")ERegion region, 
			@Param("interest")List<Einterest> interest, @Param("stack")List<Estack>stack, 
			@Param("duration")List<String> duration, Pageable pageable);
	
	List<Project> findAllByRegionIn(List<ERegion> region);
	
	List<Project> findAllByAuthor(User Author);
}

