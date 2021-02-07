package dnd.jackpot.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import dnd.jackpot.project.dto.Scrap;
import dnd.jackpot.project.dto.ScrapId;

public interface ScrapRepository extends JpaRepository<Scrap, ScrapId>{

}
