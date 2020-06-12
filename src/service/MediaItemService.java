package service;

import java.util.Map;

public interface MediaItemService {
	public String getAllMediaItems();
	public String getBookedItems();
	public String getMediaItemsPerType(String path);
	public String getPreviouslyBookedItems();
	public String postAddMediaItem(Map<String, String[]> postAddMediaItem);
}
