package entity;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;

import util.MediaLibraryViews;

public class Classroom {
	@JsonView({MediaLibraryViews.RetriveUsers.class, MediaLibraryViews.RetriveUser.class})
	private int id;
	@JsonView({MediaLibraryViews.RetriveUsers.class, MediaLibraryViews.RetriveUser.class})
	private String classroomName;
	@JsonIgnore
	private List<User> teachers = new ArrayList<User>();
	@JsonIgnore
	private List<User> students = new ArrayList<User>();
	
	public Classroom () {}
	
	public Classroom(int id, String classroomName) {
		this.id = id;
		this.classroomName = classroomName;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getClassroomName() {
		return classroomName;
	}
	public void setClassroomName(String classroomName) {
		this.classroomName = classroomName;
	}
	public List<User> getTeachers() {
		return teachers;
	}
	public void setTeachers(List<User> teachers) {
		this.teachers = teachers;
	}
	public List<User> getStudents() {
		return students;
	}
	public void setStudents(List<User> students) {
		this.students = students;
	}
	
}
