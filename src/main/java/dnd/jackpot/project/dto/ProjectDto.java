package dnd.jackpot.project.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

import dnd.jackpot.user.UserDto;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor
public class ProjectDto {
	
	
	private int id = 0;
	private String shortDesc="";
//	private List<StackDto> stacks = new ArrayList<>();
//	private UserDto author =new AuthorDto();//이 부분확인 해보기 User로 할지 writeProject로 이용할지
//	private Boolean privacy = false; 확실하지 않음
	private String title = "";
	private String createdDateTime;
//	private String region;
//	private String recruitmentPeriod;
	
	
}
