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
@Table(name="position_project")
public class ProjectPosition {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name="FK_position_project")
	private Project project;
	
	@Enumerated(EnumType.STRING)
	private Eposition position;
	
	public static ProjectPosition of(Project project, Eposition position) {
		ProjectPosition projPosition = new ProjectPosition();
		projPosition.project = project;
		projPosition.position= position;
		return projPosition;
	}
}
