package dao;

import java.util.List;

import entity.ItemType;
import entity.MediaItem;

public interface MediaItemDao {
	public List<MediaItem> getMediaItemsCurrentlyBooked();
	public List<MediaItem> getAllMediaItems();
	public List<MediaItem> getMediaItemsPerType(int itemTypeId);
	public List<MediaItem> getMediaItemsPreviouslyBooked();
	public List<MediaItem> getMediaItemsForUser(int userId, boolean isBooked);
	public ItemType getItemType(int itemTypeId);
	public Boolean addMediaItem(MediaItem mediaItem);
}
