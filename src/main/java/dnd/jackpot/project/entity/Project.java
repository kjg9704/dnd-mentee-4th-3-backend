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
	
	@ManyToOne
	private User author;
	
	
	@CreationTimestamp
	private LocalDateTime createdAt;
	
	@UpdateTimestamp
	private LocalDateTime updatedAt;
	

	@Enumerated(EnumType.STRING)
	private ERegion region;

	private String shortDesc;

	private String online;

	private String duration;
	
	private boolean  commentExist = false;
	
	private boolean memberExist = false;
	
	@Enumerated(EnumType.STRING)
	private EprojectStatus status;

	@Enumerated(EnumType.STRING)
	@ElementCollection(targetClass = Estack.class)
	@OneToMany(cascade = CascadeType.ALL,orphanRemoval=true,fetch=FetchType.LAZY, mappedBy="project")
	private final List<ProjectStack> stack = new ArrayList<>();
	
	@Enumerated(EnumType.STRING)
	private Einterest interest;
	
	@Enumerated(EnumType.STRING)
	@ElementCollection(targetClass = Eposition.class)
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch=FetchType.LAZY, mappedBy="project")
	private final List<ProjectPosition> position = new ArrayList<>();
	
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch=FetchType.LAZY, mappedBy="project")
	private final List<Comment> comment = new ArrayList<>();
	
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch=FetchType.LAZY, mappedBy="project")
	private final List<Scrap> scrap = new ArrayList<>();
	
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch=FetchType.LAZY, mappedBy="project")
	private final List<ProjectParticipant> participant = new ArrayList<>();
	
	public static Project of(String shortdesc, String title, ERegion region, Einterest interest, String online, String duration, User author) {
		Project project = new Project();
		project.title = title;
		project.shortDesc = shortdesc;
		project.region = region;
		project.duration = duration;
		project.online = online;
		project.author = author;
		project.interest = interest;
		return project;
	}
	public void update(String title, String shortDesc, ERegion region, String online, String duration) {
		this.region = region;
		this.title = title;
		this.duration = duration;
		this.online = online;
		this.shortDesc = shortDesc;
	}
}
