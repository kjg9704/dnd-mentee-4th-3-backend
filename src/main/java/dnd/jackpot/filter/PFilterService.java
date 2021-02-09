package dnd.jackpot.filter;

import java.util.List;

public interface PFilterService {
	List<PFilterDto> getAll(PFilterSearchDto searchDto);
}
