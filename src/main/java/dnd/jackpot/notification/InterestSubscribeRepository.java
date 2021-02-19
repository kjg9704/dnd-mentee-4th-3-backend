package dnd.jackpot.notification;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import dnd.jackpot.project.entity.Einterest;
import dnd.jackpot.user.User;


public interface InterestSubscribeRepository extends JpaRepository<InterestSubscribe, Long> {
	Optional<InterestSubscribe> findByInterestAndUser(Einterest interest, User user);
}
