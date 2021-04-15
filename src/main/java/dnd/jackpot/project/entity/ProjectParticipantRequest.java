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
		name="ProjectParticipantRequest",
		uniqueConstraints={
			@UniqueConstraint(
				columnNames={"project_id","userindex"}
			)
		}
	)
@NoArgsConstructor
public class ProjectParticipantRequest {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@ManyToOne
	@JoinColumn(name = "userindex")
	private User user;
	
	@ManyToOne
	@JoinColumn(name = "project_id")
	private Project project;
	
	@ManyToOne
	@JoinColumn(name = "Author_id")
	private User author;
	
	@Builder
	public ProjectParticipantRequest(Project project, User user, User author) {
		this.project = project;
		this.user = user;
		this.author = author;
	}
}
