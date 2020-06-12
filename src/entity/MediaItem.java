package entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;

import util.MediaLibraryViews;

public class MediaItem {
	@JsonIgnore
	private int id;
	@JsonView(MediaLibraryViews.RetriveUser.class)
	private ItemType itemType;
	@JsonView(MediaLibraryViews.RetriveUser.class)
	private String title;
	@JsonView(MediaLibraryViews.RetriveUser.class)
	private String authorFirstName;
	@JsonView(MediaLibraryViews.RetriveUser.class)
	private String authorLastName;
	@JsonView(MediaLibraryViews.RetriveUser.class)
	private int releasedYear;
	@JsonIgnore
	private User currentUser;
	@JsonIgnore
	private List<User> previousUsers;
	
	public MediaItem() {}
	
	public MediaItem (int id, String title, String authorFirstName, String authorLastName, int releasedYear, ItemType itemType) {
		this.id = id;
		this.title = title;
		this.authorFirstName = authorFirstName;
		this.authorLastName = authorLastName;
		this.releasedYear = releasedYear;
		this.itemType = itemType;
	}
	
	public MediaItem (String title, String authorFirstName, String authorLastName, int releasedYear, ItemType itemType) {
		this.title = title;
		this.authorFirstName = authorFirstName;
		this.authorLastName = authorLastName;
		this.releasedYear = releasedYear;
		this.itemType = itemType;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public ItemType getItemType() {
		return itemType;
	}
	public void setItemType(ItemType itemType) {
		this.itemType = itemType;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getAuthorFirstName() {
		return authorFirstName;
	}
	public void setAuthorFirstName(String authorFirstName) {
		this.authorFirstName = authorFirstName;
	}
	public String getAuthorLastName() {
		return authorLastName;
	}
	public void setAuthorLastName(String authorLastName) {
		this.authorLastName = authorLastName;
	}
	public int getReleasedYear() {
		return releasedYear;
	}
	public void setReleasedYear(int releasedYear) {
		this.releasedYear = releasedYear;
	}
	public User getCurrentUser() {
		return currentUser;
	}
	public void setCurrentUser(User currentUser) {
		this.currentUser = currentUser;
	}
	public List<User> getPreviousUsers() {
		return previousUsers;
	}
	public void setPreviousUsers(List<User> previousUsers) {
		this.previousUsers = previousUsers;
	}
	
}
