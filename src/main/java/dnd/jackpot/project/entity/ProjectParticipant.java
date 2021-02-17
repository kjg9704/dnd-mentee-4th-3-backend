package dnd.jackpot.project.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import dnd.jackpot.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(
		name="ProjectParticipant",
		uniqueConstraints={
			@UniqueConstraint(
				columnNames={"project_id","userindex"}
			)
		}
	)
@NoArgsConstructor
public class ProjectParticipant {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@ManyToOne
	@JoinColumn(name = "userindex")
	private User user;
	
	@ManyToOne
	@JoinColumn(name = "project_id")
	private Project project;
	
	@Builder
	public ProjectParticipant(Project project, User user) {
		this.project = project;
		this.user = user;
	}
	
}
