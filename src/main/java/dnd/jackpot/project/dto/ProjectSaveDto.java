package dnd.jackpot.project.dto;

import java.util.List;

import dnd.jackpot.project.entity.ERegion;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor
public class ProjectSaveDto {
    private String title;
	private String shortDesc;
	private String online;
	private String region;
	private String duration;
	private List<String> stacks;//디자이너 더하기->디자이너 개발자 테이블 만드는것 고려
	private String interest;
	private List<String> position;
}
