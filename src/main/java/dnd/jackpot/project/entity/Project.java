package dnd.jackpot.project.entity;

import java.util.ArrayList;
import java.time.LocalDateTime;
//import java.util.Collection; 필요없을시 삭제
import java.util.List;

import javax.persistence.*;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

//import dnd.jackpot.user.User;
import lombok.*;


@Entity
@Table(name="project_post")
@Getter
public class Project {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
//	@NotBlank
//	private String title;
	
//	@NotBlank
//	private String contact;

//	나중에 USER API나오면 연동
//	@ManyToOne
//	@JoinColumn
//	private User author;
	
	@CreationTimestamp
	private LocalDateTime createdAt;
	
//	@UpdateTimestamp
//	private LocalDateTime updatedAt;
	
//	private int recruiting;
//	private int applicant;
//	private String region;
//	
//	private int duration;
//	
	private String shortDesc;
	
////	topic과 차별성 고려
//	private String purpose;
//	
////	필요할지 생각해보기
//	private boolean privacy;
//	
//	private int clicks;
//	
////	목적이랑 차별성 생각해보기
//	private String topic; 
//	
//	private int recruitmentPeriod;
//	
//	@OneToMany(fetch = FetchType.LAZY, mappedBy="")
//	private final List<ProjectStack> stacks = new ArrayList<>();
//	
//	@OneToMany(fetch = FetchType.LAZY, mappedBy="")
//	private final List<ProjectWrite> write = new ArrayList<>();
//	
//	@OneToMany(fetch = FetchType.LAZY, mappedBy="")
////	private final List<ProjectKeep> keeps = new ArrayList<>();
////	
//	@OneToMany(fetch = FetchType.LAZY, mappedBy="")
//	private final List<ProjectComment> comments = new ArrayList<>();
//	
//	
//	public static Project of(String shortdesc, User author) {
//		Project project = new Project();
//		project.shortDesc = shortdesc;
////		project.author = author;
//		return project;
//	}
//	test code -> without user
	public static Project of(String shortdesc) {
		Project project = new Project();
		project.shortDesc = shortdesc;
		return project;
	}
	public void update(String title, String shortDesc) {
		this.shortDesc = shortDesc;
	}
}
