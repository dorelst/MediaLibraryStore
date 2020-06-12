package entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class UserGroup {
	@JsonIgnore
	private int id;
	private String value;
	
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
	
	public UserGroup() {}
	
	public UserGroup(int id, String value) {
		this.id = id;
		this.value = value;
	}

	
	
}
