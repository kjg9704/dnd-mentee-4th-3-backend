package dnd.jackpot.project.entity;

import java.util.ArrayList;
import java.time.LocalDateTime;
//import java.util.Collection; 필요없을시 삭제
import java.util.List;

import javax.persistence.*;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import dnd.jackpot.region.entity.ERegion;
import dnd.jackpot.stack.entity.EstackProgrammer;
//import dnd.jackpot.user.User;
import lombok.*;


@Entity
@Table(name="project_post")
@Getter @Setter
public class Project {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
//	@NotBlank
	private String title;
	
//	@NotBlank
//	private String contact;

//	나중에 USER API나오면 연동
//	@ManyToOne
//	@JoinColumn
//	private User author;
	
	@CreationTimestamp
	private LocalDateTime createdAt;
	
	@UpdateTimestamp
	private LocalDateTime updatedAt;
	
//	private int recruiting;
//	private int applicant;
	
	@Enumerated(EnumType.STRING)
	private ERegion region;
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
//	projectStack에 넣음
//	private ProjectStack stack;
	@Enumerated(EnumType.STRING)
	@ElementCollection(targetClass = EstackProgrammer.class)
	@OneToMany(cascade = CascadeType.ALL,orphanRemoval=true,fetch=FetchType.LAZY, mappedBy="project")
	private final List<ProjectStack> stacks = new ArrayList<>();
	
	@Enumerated(EnumType.STRING)
	@ElementCollection(targetClass = Einterest.class)
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch=FetchType.LAZY, mappedBy="project")
	private final List<ProjectInterest> interest = new ArrayList<>();
	
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
	public static Project of(String shortdesc, String title, ERegion region) {
		Project project = new Project();
		project.title = title;
		project.shortDesc = shortdesc;
		project.region = region;
		return project;
	}
	public void update(String title, String shortDesc, ERegion region) {
//		Project project = new Project();
		this.region = region;
		this.title = title;
		this.shortDesc = shortDesc;
//		return project;
	}
}
