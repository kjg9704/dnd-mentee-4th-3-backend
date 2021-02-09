package dnd.jackpot.filter;

import dnd.jackpot.project.entity.ERegion;
import dnd.jackpot.project.entity.Einterest;
import dnd.jackpot.project.entity.EstackProgrammer;
import lombok.Data;

@Data
public class PFilterSearchDto {
	private EstackProgrammer stackFilter;
	private ERegion regionFilter;
	private Einterest interestFilter;
}
