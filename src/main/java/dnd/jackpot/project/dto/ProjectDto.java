package dnd.jackpot.project.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

import dnd.jackpot.region.entity.ERegion;
import dnd.jackpot.stack.entity.EstackProgrammer;
import dnd.jackpot.stack.entity.StackDto;
import dnd.jackpot.user.UserDto;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor
public class ProjectDto {
	private Long id = 0L;
	private String shortDesc="";
	private List<StackDto> stacks = new ArrayList<>();
//	private UserDto author =new AuthorDto();//이 부분확인 해보기 User로 할지 writeProject로 이용할지
//	private Boolean privacy = false; 확실하지 않음
	private String title = "";
	private String createdDateTime;
	private ERegion region;
	private String recruitmentPeriod;	
}
