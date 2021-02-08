package dnd.jackpot.project.entity;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;

@Entity @Getter @Setter
@Table(name = "stack_project")
public class ProjectStack {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "FK_stack_project")
	private Project project;
	
	
	@Enumerated(EnumType.STRING)
//	@OneToMany(fetch = FetchType.EAGER, mappedBy="stack_user")
	private EstackProgrammer stack ;
	
	public static ProjectStack of(Project project, EstackProgrammer stackProgram) {
		ProjectStack projstack = new ProjectStack();
		projstack.project = project;
		projstack.stack=stackProgram;
		return projstack;
	}
	
	
}
