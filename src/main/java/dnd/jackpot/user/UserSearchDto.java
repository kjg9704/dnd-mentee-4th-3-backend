package dnd.jackpot.user;

import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor
public class UserSearchDto {

	private String position;
	
	private List<String> regionFilter;

	private List<String> stackFilter;
//	private ESortType sortType;
	@NotNull
	private Integer pageNumber;
	@Size(min=1)
	private Integer pageSize;
}
