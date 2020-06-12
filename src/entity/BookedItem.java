package entity;

public class BookedItem {
	private int id;
	private int mediaItemId;
	private int userId;
	private boolean currentlyBooked;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getMediaItemId() {
		return mediaItemId;
	}
	public void setMediaItemId(int mediaItemId) {
		this.mediaItemId = mediaItemId;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public boolean isCurrentlyBooked() {
		return currentlyBooked;
	}
	public void setCurrentlyBooked(boolean currentlyBooked) {
		this.currentlyBooked = currentlyBooked;
	}

}
