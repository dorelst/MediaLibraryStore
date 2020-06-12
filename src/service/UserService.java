package service;

import java.util.Map;

public interface UserService {
	public String getClassroomMembers(String path);
	public String getClassrooms();
	public String getUsersInfo();
	public String getUser(String path);
	public String getUsersCurrentlyBooking();
	public String postAddUser(Map<String, String[]> postAddUser);
}
