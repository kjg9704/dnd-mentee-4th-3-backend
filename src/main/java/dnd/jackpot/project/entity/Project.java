package dnd.jackpot.project.entity;

import java.util.ArrayList;
import java.time.LocalDateTime;
//import java.util.Collection; 필요없을시 삭제
import java.util.List;

import javax.persistence.*;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import dnd.jackpot.user.User;
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

	
	@ManyToOne
	private User author;
	
	
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

	private String online;

	private String duration;

//	private int recruitmentPeriod;
//	
//	projectStack에 넣음
//	private ProjectStack stack;
	@Enumerated(EnumType.STRING)
	@ElementCollection(targetClass = EstackProgrammer.class)
	@OneToMany(cascade = CascadeType.ALL,orphanRemoval=true,fetch=FetchType.LAZY, mappedBy="project")
	private final List<ProjectStack> stack = new ArrayList<>();
	
	@Enumerated(EnumType.STRING)
	@ElementCollection(targetClass = Einterest.class)
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch=FetchType.LAZY, mappedBy="project")
//	@JoinTable(name = "interest_project", joinColumns = @Joincolumn(name=""))
	private final List<ProjectInterest> interest = new ArrayList<>();
	
	@Enumerated(EnumType.STRING)
	@ElementCollection(targetClass = Eposition.class)
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch=FetchType.LAZY, mappedBy="project")
	private final List<ProjectPosition> position = new ArrayList<>();
	
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch=FetchType.LAZY, mappedBy="project")
	private final List<Scrap> scrap = new ArrayList<>();
	
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch=FetchType.LAZY, mappedBy="project")
	private final List<ProjectParticipant> participant = new ArrayList<>();
	
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
	public static Project of(String shortdesc, String title, ERegion region, String online, String duration, User author) {
		Project project = new Project();
		project.title = title;
		project.shortDesc = shortdesc;
		project.region = region;
		project.duration = duration;
		project.online = online;
		project.author = author;
		return project;
	}
	public void update(String title, String shortDesc, ERegion region, String online, String duration) {
//		Project project = new Project();
		this.region = region;
		this.title = title;
		this.duration = duration;
		this.online = online;
		this.shortDesc = shortDesc;
//		return project;
	}
}
