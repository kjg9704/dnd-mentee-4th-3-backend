package dnd.jackpot.project.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import dnd.jackpot.project.entity.Scrap;
import dnd.jackpot.user.User;


public interface ScrapRepository extends JpaRepository<Scrap, Long>{

	List<Scrap> findAllByUser(User user);
}
