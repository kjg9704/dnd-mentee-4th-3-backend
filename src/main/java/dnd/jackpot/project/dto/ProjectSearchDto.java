package dnd.jackpot.project.dto;



import java.util.ArrayList;
import java.util.List;



import org.jetbrains.annotations.NotNull;

import dnd.jackpot.project.entity.ERegion;
import dnd.jackpot.project.entity.ESortType;
import dnd.jackpot.project.entity.Einterest;
import dnd.jackpot.project.entity.EstackProgrammer;
import lombok.Data;

@Data
public class ProjectSearchDto {

	private List<ERegion> regionFilter = new ArrayList<>();
	private List<Einterest> interestFilter = new ArrayList<>();
	private List<EstackProgrammer> stackFilter = new ArrayList<>();
	private ESortType sortType;
	@NotNull
	private Integer pageNumber;
	//@Size(min=1)
	private Integer pageSize;
	
}
