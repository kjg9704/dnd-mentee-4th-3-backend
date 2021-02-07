package dnd.jackpot.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import dnd.jackpot.project.entity.Scrap;
import dnd.jackpot.project.entity.ScrapId;

public interface ScrapRepository extends JpaRepository<Scrap, ScrapId>{

}
