package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import entity.Classroom;
import entity.MediaItem;
import entity.User;
import entity.UserGroup;
import util.JDBCConnectionORACLE;

public class UserDaoORACLEImpl implements UserDao {

	public static Connection connection = JDBCConnectionORACLE.getConnection();
	private MediaItemDao mediaItemDao = new MediaItemDaoORACLEImpl();
	
	@Override
	public List<User> getUsersInfo() {		 

		try {
			
			if (connection == null) {
				return Collections.emptyList();
			}
			
			PreparedStatement statement = connection.prepareStatement("SELECT * FROM users ORDER BY last_name ASC, first_name ASC, id ASC");
			ResultSet retrieveUsers = statement.executeQuery();
			if (retrieveUsers == null) {
				return Collections.emptyList();
			}
			
			List<User> users = new ArrayList<User>();
			while (retrieveUsers.next()) {				
				User user = setUserBasicInfo(retrieveUsers);
				users.add(user);				
			}
			return users;
			
		} catch (Exception e) {
			e.printStackTrace();
			return Collections.emptyList();
		}
	}

	private User setUserBasicInfo(ResultSet retrieveUser) throws SQLException {
		UserGroup group = getUserGroup(retrieveUser.getInt("group_id"));
		List<Classroom> classrooms = retreiveUserClassrooms(retrieveUser.getInt("id"));
		User user = new User(retrieveUser.getInt("id"), 
				retrieveUser.getString("first_name"), 
				retrieveUser.getString("last_name"), 
				retrieveUser.getString("username"),
				group,
				classrooms);
		return user;
	}

	@Override
	public User getUserInfo(int userId) {

		try {
			PreparedStatement statement = connection.prepareStatement("SELECT * FROM users WHERE id = ?");
			statement.setString(1, Integer.toString(userId));
			ResultSet retrieveUser = statement.executeQuery();
			if (retrieveUser.next()) {
				UserGroup group = getUserGroup(retrieveUser.getInt("group_id"));
				User user = new User(retrieveUser.getInt("id"),
						retrieveUser.getString("first_name"),
						retrieveUser.getString("last_name"),
						retrieveUser.getString("username"),
						group,
						mediaItemDao.getMediaItemsForUser(retrieveUser.getInt("id"), true),
						mediaItemDao.getMediaItemsForUser(retrieveUser.getInt("id"), false),
						retreiveUserClassrooms(retrieveUser.getInt("id"))
						);
				return user;
			} else {
				return null;
			}			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	private List<Classroom> retreiveUserClassrooms(int userId) {
		List<Classroom> classrooms = new ArrayList<Classroom>();
		try {
			PreparedStatement statement = connection.prepareStatement("SELECT * FROM classroom WHERE id IN (SELECT classroom_id FROM classroom_member WHERE user_id = ?) ORDER BY classroom_name");
			statement.setString(1, Integer.toString(userId));
			ResultSet retreiveClassrooms = statement.executeQuery();
			if (retreiveClassrooms == null) {
				return Collections.emptyList();
			}
			while (retreiveClassrooms.next()) {
				Classroom classroom = new Classroom(retreiveClassrooms.getInt("id"),
						retreiveClassrooms.getString("classroom_name"));
				classrooms.add(classroom);
			}
			
			return classrooms;
		} catch (Exception e) {
			e.printStackTrace();
			return Collections.emptyList();
		}
		
	}

	@Override
	public List<Classroom> getClassrooms() {
		try {
			PreparedStatement statement = connection.prepareStatement("SELECT * FROM classroom ORDER BY classroom_name");
			ResultSet retrieveClassrooms = statement.executeQuery();
			if (retrieveClassrooms == null) {
				return Collections.emptyList();
			}
			
			List<Classroom> classrooms = new ArrayList<Classroom>();
			while (retrieveClassrooms.next()) {
				Classroom classroom = new Classroom(retrieveClassrooms.getInt("id"),
						retrieveClassrooms.getString("classroom_name"));
				classrooms.add(classroom);
				
			}
			return classrooms;
			
		} catch (Exception e) {
			e.printStackTrace();
			return Collections.emptyList();
		}
	}

	@Override
	public List<User> getClassroomMembers(int classroomId) {
		try {
			PreparedStatement statement = connection.prepareStatement("SELECT * FROM users WHERE id IN (SELECT user_id FROM classroom_member WHERE classroom_id = ?) ORDER BY group_id DESC, last_name ASC, first_name ASC, id ASC");
			statement.setString(1, Integer.toString(classroomId));
			ResultSet retrieveUsers = statement.executeQuery();
			if (retrieveUsers == null) {
				return Collections.emptyList();
			}
			
			List<User> users = new ArrayList<User>();
			while (retrieveUsers.next()) {
				User user = setUserBasicInfo(retrieveUsers);
				users.add(user);				
			}
			return users;
			
		} catch (Exception e) {
			e.printStackTrace();
			return Collections.emptyList();
		}
	}

	@Override
	public UserGroup getUserGroup(int groupId) {
		try {
			UserGroup group;
			PreparedStatement statement = connection.prepareStatement("SELECT * FROM user_group WHERE id = ?");
			statement.setString(1, Integer.toString(groupId));
			ResultSet retrieveUserGroup = statement.executeQuery();
			if (retrieveUserGroup.next()) {
				group = new UserGroup(retrieveUserGroup.getInt("id"), 
						retrieveUserGroup.getString("value"));
				return group;

			} else {
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<User> getUsersCurrentlyBookingMaterials() {
		try {
			PreparedStatement statement = connection.prepareStatement("SELECT * FROM users WHERE id IN (SELECT DISTINCT user_id FROM booked_item WHERE currently_booked = ?) ORDER BY last_name ASC, first_name ASC, group_id ASC, id ASC");
			statement.setBoolean(1, true);
			ResultSet retrieveUsers = statement.executeQuery();
			if (retrieveUsers == null) {
				return Collections.emptyList();
			}
			
			List<User> users = new ArrayList<User>();
			while (retrieveUsers.next()) {
				User user = setUserBasicInfo(retrieveUsers);
				users.add(user);				
			}
			return users;
			
		} catch (Exception e) {
			e.printStackTrace();
			return Collections.emptyList();
		}
	}
	
	@Override
	public Boolean addUser(User user) {
		
		try {
			PreparedStatement statement = connection.prepareStatement("INSERT INTO users VALUES (id_maker.nextval, ?, ?, ?, ?, ?)");
			statement.setInt(1, user.getUserGroup().getId());
			statement.setString(2, user.getFirstName());
			statement.setString(3, user.getLastName());
			statement.setString(4, user.getUsername());
			statement.setString(5, user.getPassword());
			
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
