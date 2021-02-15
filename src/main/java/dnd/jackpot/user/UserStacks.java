package dnd.jackpot.user;

import javax.persistence.Entity;

import com.sun.istack.NotNull;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
@NoArgsConstructor
public class UserStacks {
	
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
    
    @NotNull
	private String stack;
	
    @NotNull
    @ManyToOne
    @JoinColumn(name = "userindex", updatable = true)
    private User user;
	
	@Builder
	public UserStacks(String stack, User user) {
		this.stack = stack;
		this.user = user;
	}
}
