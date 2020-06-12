package dao;

import java.util.List;

import entity.Classroom;
import entity.MediaItem;
import entity.User;
import entity.UserGroup;

public interface UserDao {
	public List<User> getUsersInfo();
	public User getUserInfo(int userId);
	public List<Classroom> getClassrooms();
	public List<User> getClassroomMembers(int classroomId);
	public UserGroup getUserGroup(int groupId);
	public List<User> getUsersCurrentlyBookingMaterials();
	public Boolean addUser(User user);
}
