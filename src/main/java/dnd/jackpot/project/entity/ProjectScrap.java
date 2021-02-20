package dnd.jackpot.project.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import dnd.jackpot.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(
		name="Projectscrap",
		uniqueConstraints={
			@UniqueConstraint(
				columnNames={"project_id","user_user_index"}
			)
		}
	)
public class ProjectScrap {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long scrapId;
	
	@ManyToOne
//	@JoinColumn(name = "project_post_id", updatable = true)
	private Project project ;
	
	@ManyToOne
	private User user;
	
	@Builder
	public ProjectScrap(Project project, User user) {
		this.project = project;
		this.user = user;
	}
}