package dnd.jackpot.project.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import dnd.jackpot.project.entity.ERegion;
import dnd.jackpot.project.entity.ESortType;
import dnd.jackpot.project.entity.EstackProgrammer;
import lombok.Data;

@Data
public class ProjectSearchDto {
	private ERegion regionFilter;
	private EstackProgrammer stackFilter;
	private ESortType sortType;
//	@NotNull
//	private Integer pageNumber;
//	@Size(min=1)
//	private Integer pageSize;
	
}
