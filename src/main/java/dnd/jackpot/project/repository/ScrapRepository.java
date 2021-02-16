package dnd.jackpot.project.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import dnd.jackpot.project.entity.Scrap;
import dnd.jackpot.project.entity.ScrapId;

public interface ScrapRepository extends JpaRepository<Scrap, ScrapId>{

//	List<Scrap> findAllByUserIndex(long userIndex);
}
