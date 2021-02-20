package dnd.jackpot.user;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserScrapRepository extends JpaRepository<UserScrap, Long>{
	void deleteByUserAndScrapedUser(User user, long userIndex);
}
