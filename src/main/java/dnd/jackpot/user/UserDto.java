package dnd.jackpot.user;

import java.util.ArrayList;
import java.util.List;

import dnd.jackpot.project.dto.ProjectDto;
import dnd.jackpot.project.dto.ProjectParticipantRequestDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDto {
	
  private String email;
  private String password;
 
  private String name;
  private String region;
  private String position;
  private List<String> stacks = new ArrayList<>();
  private boolean privacy;
  private String loginType;
  private String career;
  private String auth;
  private String emoticon;
  private String introduction;
  
  @Getter
  @AllArgsConstructor
  public static class profileResponse {
	  private String name;
	  private String region;
	  private String position;
	  private List<String> stacks = new ArrayList<>();
	  private boolean privacy;
	  private String loginType;
	  private String career;
	  private String auth;
	  private String emoticon;
	  private String introduction;
	  
	  private List<ProjectDto> myprojects = new ArrayList<>();
	  private List<ProjectDto> participantProject = new ArrayList<>();
	  private List<ProjectParticipantRequestDto> participantRequest = new ArrayList<>();
  }
  
  @Getter
  @AllArgsConstructor
  public static class otherResponse {
	  private String name;
	  private String region;
	  private String position;
	  private List<String> stacks = new ArrayList<>();
	  private boolean privacy;
	  private String career;
	  private String auth;
	  private String emoticon;
	  private String introduction;
	  private List<ProjectDto> projectId = new ArrayList<>();
	  private List<ProjectDto> participantProject = new ArrayList<>();
  }
  
  @Getter
  @AllArgsConstructor
  public static class simpleResponse {
	  private long userIndex;
	  private String region;
	  private String position;
	  private String career;
	  private String emoticon;
  }
  
}
