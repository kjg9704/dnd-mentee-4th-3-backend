package dnd.jackpot.user;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import dnd.jackpot.project.entity.ERegion;
import dnd.jackpot.project.entity.Estack;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
  Optional<User> findByEmailAndLogintype(String email, String loginType);
  Optional<User> findById(Long id);
  Boolean existsByEmail(String email);
  Boolean existsByName(String email);
  void deleteByEmailAndLogintype(String email, String loginType);
  Optional<User> findByEmail(String email);
  @Query(value = "SELECT p FROM User p "
			+ "JOIN p.stacks s "
			+ "WHERE (((:region) is null) OR (p.region in (:region))) "
			+ "AND (((:stack) is null) OR (s.stack in (:stack))) "
  			+ "AND (((:position) is null) OR (p.position in (:position))) GROUP BY p ORDER BY p.scrapedUsers.size DESC")
	Page<User> findAllByRegionInAndStackInAndPositionORDERBYsize(@Param("region")ERegion region,
			@Param("stack")List<Estack>stack, @Param("position") List<String> position ,Pageable pageable);
  @Query(value = "SELECT p FROM User p "
			+ "JOIN p.stacks s "
			+ "WHERE (((:region) is null) OR (p.region in (:region))) "
			+ "AND (((:stack) is null) OR (s.stack in (:stack))) "
			+ "AND (((:position) is null) OR (p.position in (:position))) GROUP BY p ORDER BY p.createdAt DESC")
	Page<User> findAllByRegionInAndStackInAndPositionORDERBYcreated(@Param("region")ERegion region,
			@Param("stack")List<Estack>stack, @Param("position") List<String> position ,Pageable pageable);
}