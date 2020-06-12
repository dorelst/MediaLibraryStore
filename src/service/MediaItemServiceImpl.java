package service;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.ObjectMapper;

import dao.MediaItemDao;
import dao.MediaItemDaoImpl;
import dao.MediaItemDaoORACLEImpl;
import entity.Classroom;
import entity.ItemType;
import entity.MediaItem;
import util.MediaLibraryViews;

public class MediaItemServiceImpl implements MediaItemService {
	
	public static MediaItemDao mediaItemDao = new MediaItemDaoORACLEImpl();
	
	@Override
	public String getAllMediaItems() {
		List<MediaItem> mediaItems = mediaItemDao.getAllMediaItems();
		if (mediaItems == null || mediaItems.isEmpty()) {
			return "No media items found!";
		}
		try {
			ObjectMapper mapper = new ObjectMapper();
			String jsonString = mapper.writerWithView(MediaLibraryViews.RetriveUser.class).writeValueAsString(mediaItems);	
			return jsonString;
		} catch (Exception e) {
			return "Internal error! List of media items could not be returned.";
		}
	}
	
	@Override
	public String getBookedItems() {
		List<MediaItem> mediaItems = mediaItemDao.getMediaItemsCurrentlyBooked();
		if (mediaItems == null || mediaItems.isEmpty()) {
			return "No booked items found!";
		}
		try {
			ObjectMapper mapper = new ObjectMapper();
			String jsonString = mapper.writerWithView(MediaLibraryViews.RetriveUser.class).writeValueAsString(mediaItems);	
			return jsonString;
		} catch (Exception e) {
			return "Internal error! List of booked items could not be returned.";
		}
	}
	
	@Override
	public String getMediaItemsPerType(String path) {
		if (path == null || path.length() == 0 || path.trim().equals("/")) {
			return "No item type selected!";
		}
		
		Integer itemTypeId;
		switch (path.substring(1)) {
			case "book": itemTypeId=1;
			break;
			case "audio book": itemTypeId=2;
			break;
			case "dvd": itemTypeId=3;
			break;
			default: return "Invalid item type!";
		}
		
		List<MediaItem> mediaItems = mediaItemDao.getMediaItemsPerType(itemTypeId);
		if (mediaItems == null || mediaItems.isEmpty()) {
			return "Found No items of this type!";
		}
		
		try {
			ObjectMapper mapper = new ObjectMapper();
			String jsonString = mapper.writerWithView(MediaLibraryViews.RetriveUser.class).writeValueAsString(mediaItems);	
			return jsonString;
		} catch (Exception e) {
			return "Internal error! List of media items of this type could not be returned.";
		}
	}
	
	public Integer getId(String path) {
		try {
			path = path.substring(1);
			int id = Integer.parseInt(path);
			if (id < 1) {
				return null;
			};
			return id;
		} catch (Exception e) {
			return null;
		}
	}

	
	@Override
	public String getPreviouslyBookedItems() {

		List<MediaItem> mediaItems = mediaItemDao.getMediaItemsPreviouslyBooked();
		if (mediaItems == null || mediaItems.isEmpty()) {
			return "No previously booked items found!";
		}
		try {
			ObjectMapper mapper = new ObjectMapper();
			String jsonString = mapper.writerWithView(MediaLibraryViews.RetriveUser.class).writeValueAsString(mediaItems);	
			return jsonString;
		} catch (Exception e) {
			return "Internal error! List of previously booked items could not be returned.";
		}
	}
	
	@Override
	public String postAddMediaItem(Map<String, String[]> postAddMediaItem) {
		
		if (postAddMediaItem == null || postAddMediaItem.isEmpty()) {
			return "Something didn't went well! No form or an empty form received.";
		}
		
		MediaItem mediaItem = createMediaItem(postAddMediaItem);
		
		String validItem = validateMediaItem(mediaItem);
		Boolean result = false;
		
		if (validItem == null || validItem.isEmpty()) {
			result = mediaItemDao.addMediaItem(mediaItem);
		} else {
			return validItem;
		}
				
		if (result) {
			return "Media item successfuly added!";
		} else {
			return "Media item couldn't be added! Try again.";
		}
		
	}
	
	private MediaItem createMediaItem(Map<String, String[]> postAddMediaItem) {
		String itemType[] = postAddMediaItem.get("itemType");
		String itemTitle[] = postAddMediaItem.get("itemTitle");
		String firstName[] = postAddMediaItem.get("itemAuthorFirstName");
		String lastName[] = postAddMediaItem.get("itemAuthorLastName");
		String releasedYear[] = postAddMediaItem.get("itemReleasedYear");
		
		int itemTypeId=0;
		String value = "";
		ItemType itemTypeObj;
		switch(itemType[0]) {
			case "book": itemTypeId=1;
						value = "book";
						itemTypeObj = new ItemType(itemTypeId, value);
						break;
			case "audio book": itemTypeId=2;
						value = "audio book";
						itemTypeObj = new ItemType(itemTypeId, value);
						break;
			case "dvd": itemTypeId=3;
						value = "dvd";
						itemTypeObj = new ItemType(itemTypeId, value);
						break;
			default: itemTypeObj = null;
		}
		
		int year;
		if (releasedYear[0] == null || releasedYear[0].isEmpty()) {
			year = 0;
		} else {
			try {
				year = Integer.parseInt(releasedYear[0]);
			} catch (Exception e) {
				e.printStackTrace();
				year = 0;
			}
		}

		MediaItem mediaItem = new MediaItem(itemTitle[0], firstName[0], lastName[0], year, itemTypeObj);
		
		return mediaItem;
	}
	
	private String validateMediaItem(MediaItem mediaItem) {
		String message = "The following required filds are missing or have invalid format: ";
		Boolean valid = true;
		if (mediaItem.getTitle() == null || mediaItem.getTitle().isEmpty()) {
			message = message + "no or invalid title, ";
			valid = false;
		}
		
		if (mediaItem.getItemType() == null) {
			message = message + "no or invalid item type, ";
			valid = false;
		}
		
		if (mediaItem.getReleasedYear() == 0) {
			message = message + "no or invalid year, ";
			valid = false;
		}
		
		if ((mediaItem.getItemType() != null) && (mediaItem.getItemType().getId() != 3)) {
			if (mediaItem.getAuthorFirstName() == null || mediaItem.getAuthorFirstName().isEmpty()) {
				message = message + "no or invalid first name, ";
				valid = false;
			}
			
			if (mediaItem.getAuthorLastName() == null || mediaItem.getAuthorLastName().isEmpty()) {
				message = message + "no or invalid last name, ";
				valid = false;
			}
		}
		
		if (valid) {
			return "";
		}
		
		message = message.substring(0, message.length()-2) + ".";
		return message;
	}

}
