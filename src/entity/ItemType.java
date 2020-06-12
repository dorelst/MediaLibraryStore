package entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;

import util.MediaLibraryViews;

public class ItemType {
	@JsonIgnore
	private int id;
	@JsonView(MediaLibraryViews.RetriveUser.class)
	private String value;
	
	public ItemType() {}
	
	public ItemType(int id, String value) {
		this.id = id;
		this.value = value;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
}
