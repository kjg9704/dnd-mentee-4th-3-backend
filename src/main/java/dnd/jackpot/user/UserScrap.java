package dnd.jackpot.user;


import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(
		name="UserScrap",
		uniqueConstraints={
			@UniqueConstraint(
				columnNames={"scrappingUser", "user_user_index"}
			)
		}
	)
public class UserScrap {

	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	@ManyToOne
	private User user;

	private long scrappingUser;
	
	
	@Builder
	public UserScrap(long scrappingUser, User scrapedUser) {
		this.scrappingUser = scrappingUser;
		this.user = scrapedUser;
	}
	
}
