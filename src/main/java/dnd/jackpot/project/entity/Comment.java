package dnd.jackpot.project.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class Comment {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long commentId;
	
	private String body;
	
	private boolean privacy;
	
	private String date;
	
	@JoinColumn(name ="project_id", referencedColumnName = "project_id")
	private long projectId;
	 
	@JoinColumn(name ="user_index", referencedColumnName = "user_index")
	private long userIndex;
	
	@Builder
	public Comment(String body, boolean privacy, String date, long projectId, long userIndex) {
		this.body = body;
		this.privacy = privacy;
		this.date = date;
		this.projectId = projectId;
		this.userIndex = userIndex;
	}
}
