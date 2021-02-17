package dnd.jackpot.project.entity;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter @Entity
@Table(name = "interest_project" )
public class ProjectInterest {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name="project")
	private Project project;
	
	@Enumerated(EnumType.STRING)
	private Einterest interest;
	
	public static ProjectInterest of(Project project, Einterest interest) {
		ProjectInterest projInterest = new ProjectInterest();
		projInterest.project = project;
		projInterest.interest = interest;
		return projInterest;
	}
	
}
