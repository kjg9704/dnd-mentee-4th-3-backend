package dnd.jackpot.project.dto;

import javax.validation.constraints.NotNull;

import dnd.jackpot.project.entity.ERegion;
import dnd.jackpot.project.entity.EstackProgrammer;

public class ProjectSearchDto {
	private ERegion regionfilter;
	private EstackProgrammer stackfilter;
	
	@NotNull
	private Integer pageNumber;
	@Size(min=1)
	private Integer pageSize;
	
}
