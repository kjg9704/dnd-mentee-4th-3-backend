package dnd.jackpot.user;

import java.util.ArrayList;
import java.util.List;

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
  private String job;
  private List<String> stacks = new ArrayList<>();
  private boolean privacy;
  private String loginType;
  private String career;
  private String auth;
  
  @Getter
  @AllArgsConstructor
  public static class Response {
	  private String name;
	  private String region;
	  private String job;
	  private List<String> stacks = new ArrayList<>();
	  private boolean privacy;
	  private String loginType;
	  private String career;
	  private String auth;
  }
  
}
