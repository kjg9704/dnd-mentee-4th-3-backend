package dnd.jackpot.user;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserScrapRepository extends JpaRepository<UserScrap, Long>{
	void deleteByUserAndScrappingUser(User user, long userIndex);
	List<UserScrap> findAllByScrappingUser(long userIndex);
}
