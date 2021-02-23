package dnd.jackpot.user;

import java.util.ArrayList;
import java.util.List;

import dnd.jackpot.project.dto.ProjectDto;
import dnd.jackpot.project.dto.ProjectParticipantRequestDto;
import dnd.jackpot.project.entity.ERegion;
import dnd.jackpot.project.entity.Einterest;
import dnd.jackpot.project.entity.Estack;
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
  private String portfolioLink1;
  private String portfolioLink2;
  
  @Getter
  @AllArgsConstructor
  public static class profileResponse {
	  private long userIndex;
	  private String name;
	  private ERegion region;
	  private String position;
	  private List<Estack> stacks = new ArrayList<>();
	  private boolean privacy;
	  private String loginType;
	  private String career;
	  private String auth;
	  private String emoticon;
	  private String introduction;
	  private String portfolioLink1;
	  private String portfolioLink2;
	  private boolean commentPush;
	  private boolean requestPush;
	  private boolean requestAcceptPush;
	  private List<Einterest> subscribes = new ArrayList<>();
	  private List<ProjectDto> myprojects = new ArrayList<>();
	  private List<ProjectDto> participantProject = new ArrayList<>();
	  private List<ProjectDto> participantRequest = new ArrayList<>();
	  private List<ProjectDto> commentProjects = new ArrayList<>();
	  private List<ProjectDto> scrapProjects = new ArrayList<>();
  }
  
  @Getter
  @AllArgsConstructor
  public static class otherResponse {
	  private long userIndex;
	  private String name;
	  private ERegion region;
	  private String position;
	  private List<Estack> stacks = new ArrayList<>();
	  private boolean privacy;
	  private String career;
	  private String auth;
	  private String emoticon;
	  private String introduction;
	  private String portfolioLink1;
	  private String portfolioLink2;
  }
  
  @Getter
  @AllArgsConstructor
  public static class simpleResponse {
	  private long userIndex;
	  private String name;
	  private ERegion region;
	  private String position;
	  private String career;
	  private String emoticon;
  }
  
}
