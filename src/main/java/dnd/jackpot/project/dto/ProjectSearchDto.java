package dnd.jackpot.project.dto;



import java.util.ArrayList;
import java.util.List;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;



import dnd.jackpot.project.entity.ERegion;
import dnd.jackpot.project.entity.ESortType;
import dnd.jackpot.project.entity.Einterest;
import dnd.jackpot.project.entity.Estack;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor
public class ProjectSearchDto {


	private String regionFilter;

	private List<String> interestFilter;

	private List<String> stackFilter;

	private ESortType sortType;
	
	private List<String> duration;
	@NotNull
	private Integer pageNumber;
	@Size(min=1)
	private Integer pageSize;
	
}
