package dnd.jackpot.user;

import com.sun.istack.NotNull;

import dnd.jackpot.project.entity.Estack;
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
    @Enumerated(EnumType.STRING)
	private Estack stack;
	
    @NotNull
    @ManyToOne
    @JoinColumn(name = "userindex", updatable = true)
    private User user;
	
	@Builder
	public UserStacks(String stack, User user) {
		Estack estack = Estack.valueOf(stack);
		this.stack = estack;
		this.user = user;
	}
}
