package dnd.jackpot.notification;



import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.sun.istack.NotNull;

import dnd.jackpot.project.entity.ERegion;
import dnd.jackpot.project.entity.Einterest;
import dnd.jackpot.user.User;
import dnd.jackpot.user.UserStacks;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(
		name="interest_subscribe",
		uniqueConstraints={
			@UniqueConstraint(
				columnNames={"userindex","interest"}
			)
		}
	)
public class InterestSubscribe {

	
	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	@NotNull
	@ManyToOne
	@JoinColumn(name = "userindex", updatable = true)
	private User user;
	
	@NotNull
	@Enumerated(EnumType.STRING)
	private Einterest interest;
	
	@Builder
	public InterestSubscribe(long id, User user, Einterest interest) {
		this.id = id;
		this.user = user;
		this.interest = interest;
	}
}
