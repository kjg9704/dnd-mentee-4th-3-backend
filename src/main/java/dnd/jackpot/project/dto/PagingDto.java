package dnd.jackpot.project.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class PagingDto<T>{
	private List<T> contents = new ArrayList<>();
}
