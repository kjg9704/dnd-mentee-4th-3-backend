package dnd.jackpot.project.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import dnd.jackpot.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Getter @Setter
@Table(name = "comment_project")
public class Comment {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long commentId;
	
	private String body;
	
	private boolean privacy;
	
	private String date;
	
	@ManyToOne
	@JoinColumn(name="project")
	private Project project;
	
	@ManyToOne
	@JoinColumn(name = "user")
	private User user;
	
	@Builder
	public Comment(String body, boolean privacy, String date, Project project, User user) {
		this.body = body;
		this.privacy = privacy;
		this.date = date;
		this.project = project;
		this.user = user;
	}
}
