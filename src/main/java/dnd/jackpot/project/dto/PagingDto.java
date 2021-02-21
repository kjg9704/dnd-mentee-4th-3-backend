package dnd.jackpot.project.dto;

import java.util.ArrayList;
import java.util.List;

import dnd.jackpot.project.dto.ProjectDto.filterDto;
import lombok.Data;

@Data
public class PagingDto<T>{
	private int pageNumber;
	private int pageSize;
	private int totalPages;
	
	/*
	 * @JsonProperty("isLast")
	 * private boolean isLast;
	 * @JsonProperty("isFirst")
	 * private boolean isFirst;
	 * private List<T> contents = new ArrayList<>();
	 */
	private List<T> contents = new ArrayList<>();
}
