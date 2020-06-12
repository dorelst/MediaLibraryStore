package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import entity.ItemType;
import entity.MediaItem;
import util.JDBCConnection;
import util.JDBCConnectionORACLE;

public class MediaItemDaoORACLEImpl implements MediaItemDao {
	
	public static Connection connection = JDBCConnectionORACLE.getConnection();

	@Override
	public List<MediaItem> getMediaItemsCurrentlyBooked() {
		try {		
			if (connection == null) {
				System.out.println("no connection!");
				return Collections.emptyList();
			}
			PreparedStatement statement = connection.prepareStatement("SELECT * FROM media_item WHERE id IN (SELECT media_item_id FROM booked_item WHERE currently_booked = 1) ORDER BY title ASC, item_type_id ASC, author_last_name ASC, author_first_name ASC");
			ResultSet retrieveCurrentlyBookedMediaItems = statement.executeQuery();
			
			List<MediaItem> mediaItems = getMediaItems(retrieveCurrentlyBookedMediaItems);
			return mediaItems;
			
		} catch (Exception e) {
			e.printStackTrace();
			return Collections.emptyList();
		}
	}
	
	private List<MediaItem> getMediaItems(ResultSet retrieveMediaItems) throws SQLException {
		if (retrieveMediaItems == null) {
			return Collections.emptyList();
		}
		
		List<MediaItem> mediaItems = new ArrayList<MediaItem>();
		int i=0;
		while (retrieveMediaItems.next()) {				
			MediaItem mediaItem = setMediaItemInfo(retrieveMediaItems);
			mediaItems.add(mediaItem);
			i++;
		}
		return mediaItems;
	}
	
	private MediaItem setMediaItemInfo(ResultSet retrieveCurrentlyBookedMediaItem) throws SQLException {
		ItemType itemType = getItemType(retrieveCurrentlyBookedMediaItem.getInt("item_type_id"));				
		MediaItem mediaItem = new MediaItem(retrieveCurrentlyBookedMediaItem.getInt("id"),
				retrieveCurrentlyBookedMediaItem.getString("title"),
				retrieveCurrentlyBookedMediaItem.getString("author_first_name"), 
				retrieveCurrentlyBookedMediaItem.getString("author_last_name"), 
				retrieveCurrentlyBookedMediaItem.getInt("released_year"),
				itemType
				);
		return mediaItem;
	}

	@Override
	public List<MediaItem> getAllMediaItems() {
		try {		
			if (connection == null) {
				return Collections.emptyList();
			}
			
			PreparedStatement statement = connection.prepareStatement("SELECT * FROM media_item ORDER BY item_type_id ASC, title ASC, author_last_name ASC, author_first_name ASC");
			ResultSet retrieveAllMediaItems = statement.executeQuery();
			
			List<MediaItem> mediaItems = getMediaItems(retrieveAllMediaItems);
			return mediaItems;
			
		} catch (Exception e) {
			e.printStackTrace();
			return Collections.emptyList();
		}
	}

	@Override
	public List<MediaItem> getMediaItemsPerType(int itemTypeId) {
		try {		
			if (connection == null) {
				return Collections.emptyList();
			}
			
			PreparedStatement statement = connection.prepareStatement("SELECT * FROM media_item WHERE item_type_id = ? ORDER BY title ASC, author_last_name ASC, author_first_name ASC");
			statement.setString(1, Integer.toString(itemTypeId));
			ResultSet retrieveMediaItemsPerType = statement.executeQuery();

			List<MediaItem> mediaItems = getMediaItems(retrieveMediaItemsPerType);
			return mediaItems;
			
		} catch (Exception e) {
			e.printStackTrace();
			return Collections.emptyList();
		}
	}

	@Override
	public List<MediaItem> getMediaItemsPreviouslyBooked() {
		try {		
			if (connection == null) {
				return Collections.emptyList();
			}
			
			PreparedStatement statement = connection.prepareStatement("SELECT * FROM media_item WHERE id IN (SELECT media_item_id FROM booked_item WHERE currently_booked = 0) ORDER BY item_type_id ASC, title ASC, author_last_name ASC, author_first_name ASC");
			ResultSet retrieveCurrentlyBookedMediaItems = statement.executeQuery();
			
			List<MediaItem> mediaItems = getMediaItems(retrieveCurrentlyBookedMediaItems);
			return mediaItems;
			
		} catch (Exception e) {
			e.printStackTrace();
			return Collections.emptyList();
		}
	}

	@Override
	public List<MediaItem> getMediaItemsForUser(int userId, boolean isBooked) {	
		try {
			PreparedStatement statement = connection.prepareStatement("SELECT * FROM media_item WHERE id IN (SELECT media_item_id FROM booked_item WHERE user_id = ? AND currently_booked = ?) ORDER BY item_type_id ASC, title ASC, author_last_name ASC, author_first_name ASC");
			statement.setString(1, Integer.toString(userId));
			statement.setBoolean(2, isBooked);
			ResultSet retrieveMediaItem = statement.executeQuery();
			if (retrieveMediaItem == null) {
				return Collections.emptyList();
			}
			
			List<MediaItem> mediaItems = new ArrayList<MediaItem>();
			while (retrieveMediaItem.next()) {
				ItemType itemType = getItemType(retrieveMediaItem.getInt("item_type_id"));
				MediaItem mediaItem = new MediaItem(retrieveMediaItem.getInt("id"),
						retrieveMediaItem.getString("title"),
						retrieveMediaItem.getString("author_first_name"),
						retrieveMediaItem.getString("author_last_name"),
						retrieveMediaItem.getInt("released_year"),
						itemType);
				mediaItems.add(mediaItem);
			}

			return mediaItems;
		} catch (Exception e) {
			e.printStackTrace();
			return Collections.emptyList();
		}

	}

	@Override
	public ItemType getItemType(int itemTypeId) {
		try {
			PreparedStatement statement = connection.prepareStatement("SELECT * FROM item_type WHERE id = ?");
			statement.setString(1, Integer.toString(itemTypeId));
			ResultSet retrieveItemType = statement.executeQuery();
			if (retrieveItemType.next()) {
				ItemType itemType = new ItemType(retrieveItemType.getInt("id"), retrieveItemType.getString("value"));
				return itemType;
			} else {
				return null;
			}

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@Override
	public Boolean addMediaItem(MediaItem mediaItem) {
		
		try {
			PreparedStatement statement = connection.prepareStatement("INSERT INTO media_item VALUES (id_maker.nextval, ?, ?, ?, ?, ?)");
			statement.setInt(1, mediaItem.getItemType().getId());
			statement.setString(2, mediaItem.getTitle());
			statement.setString(3, mediaItem.getAuthorFirstName());
			statement.setString(4, mediaItem.getAuthorLastName());
			statement.setInt(5, mediaItem.getReleasedYear());
			
			int result = statement.executeUpdate();
			if (result == 1) {
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}		
		return false;
		
	}

}
