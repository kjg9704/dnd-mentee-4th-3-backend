package dnd.jackpot.user;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@Getter
@NoArgsConstructor
@Table(
		name="deleted_user",
		uniqueConstraints={
			@UniqueConstraint(
				columnNames={"userEmail","loginType"}
			)
		}
	)
public class DeletedUser  {
	
	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	private String userEmail;
	
	private String loginType;
	
	@Builder
	public DeletedUser(String userEmail, String loginType) {
		this.userEmail = userEmail;
		this.loginType = loginType;
	}
}
