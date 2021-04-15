package dnd.jackpot.user;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SnsRequestDto {
	private String snsToken;
	private String registrationToken;
}
