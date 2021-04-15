package dnd.jackpot.user;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserModifyDto {
	private String name;
	private String region;
	private String position;
	private List<String> stacks = new ArrayList<>();
	private boolean privacy;
	private String career;
	private String emoticon;
	private String introduction;
	private String portfolioLink1;
	private String portfolioLink2;
}
