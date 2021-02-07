package dnd.jackpot.project.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import lombok.Data;

@Data
@Embeddable
public class ScrapId implements Serializable {

	 @JoinColumn(name ="project_id")
	 private int postIndex;
	 
	 @JoinColumn(name ="user_userIndex")
	 private long userIndex;
}
