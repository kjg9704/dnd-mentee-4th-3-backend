package dnd.jackpot.project.dto;

import java.util.List;

import dnd.jackpot.project.entity.ERegion;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor
public class ProjectModifyDto {
	private String title;
	private String shortdesc;
	private ERegion region;
	private List<String> stack; 
	private List<String> interest;
}
