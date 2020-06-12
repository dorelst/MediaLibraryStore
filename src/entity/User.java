package entity;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.*;

import util.MediaLibraryViews;

public class User {
	@JsonView(MediaLibraryViews.RetriveUsers.class)
	private int id;
	
	@JsonView(MediaLibraryViews.RetriveUsers.class)
	private String firstName;
	
	@JsonView(MediaLibraryViews.RetriveUsers.class)
	private String lastName;
	
	@JsonView(MediaLibraryViews.RetriveUsers.class)
	private String username;
	
	@JsonIgnore
	private String password;
	
	@JsonView(MediaLibraryViews.RetriveUsers.class)
	private UserGroup userGroup;
	
	@JsonView(MediaLibraryViews.RetriveUser.class)
	private List<MediaItem> currentBookedItems = new ArrayList<MediaItem>();
	
	@JsonView(MediaLibraryViews.RetriveUser.class)
	private List<MediaItem> previouslyBookedItems = new ArrayList<MediaItem>();
	
	@JsonView({MediaLibraryViews.RetriveUsers.class, MediaLibraryViews.RetriveUser.class})
	private List<Classroom> classrooms = new ArrayList<Classroom>();
	
	public User() {}
	
	public User(int id, String firstName, String lastName, String username, UserGroup group, List<Classroom> classrooms) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.username = username;
		this.userGroup = group;
		this.classrooms = classrooms;
	}
	
	public User(String firstName, String lastName, String username, String password, UserGroup group) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.username = username;
		this.password = password;
		this.userGroup = group;
	}
	
	public User(int id, String firstName, String lastName, String username, UserGroup group, List<MediaItem> currentlyBookedMediaItems, List<MediaItem> previouslyBookedMediaItems, List<Classroom> classrooms) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.username = username;
		this.userGroup = group;
		this.currentBookedItems = currentlyBookedMediaItems;
		this.previouslyBookedItems = previouslyBookedMediaItems;
		this.classrooms = classrooms;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public UserGroup getUserGroup() {
		return userGroup;
	}
	public void setUserGroup(UserGroup userGroup) {
		this.userGroup = userGroup;
	}
	public List<MediaItem> getCurrentBookedItems() {
		return currentBookedItems;
	}
	public void setCurrentBookedItems(List<MediaItem> currentBookedItems) {
		this.currentBookedItems = currentBookedItems;
	}
	public List<MediaItem> getPreviouslyBookedItems() {
		return previouslyBookedItems;
	}
	public void setPreviouslyBookedItems(List<MediaItem> previouslyBookedItems) {
		this.previouslyBookedItems = previouslyBookedItems;
	}
	public List<Classroom> getClassrooms() {
		return classrooms;
	}
	public void setClassrooms(List<Classroom> classrooms) {
		this.classrooms = classrooms;
	}
	
}
