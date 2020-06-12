package service;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.ObjectMapper;

import dao.UserDao;
import dao.UserDaoImpl;
import dao.UserDaoORACLEImpl;
import entity.Classroom;
import entity.ItemType;
import entity.MediaItem;
import entity.User;
import entity.UserGroup;
import util.MediaLibraryViews;

public class UserServiceImpl implements UserService {
	
	public static UserDao userDao = new UserDaoORACLEImpl();
	
	@Override
	public String getClassroomMembers(String path) {

		if (path == null || path.length() == 0 || path.trim().equals("/")) {
			return "No classroom selected!";
		}
		
		Integer id = getId(path);
		if (id == null) {
			return "Invalid classroom ID!";
		}
		
		List<User> users = userDao.getClassroomMembers(id);
		if (users == null || users.isEmpty()) {
			return "No classroom with id " + id + " found!";
		}
		try {
			ObjectMapper mapper = new ObjectMapper();
			String jsonString = mapper.writerWithView(MediaLibraryViews.RetriveUsers.class).writeValueAsString(users);	
			return jsonString;
		} catch (Exception e) {
			return "Internal error! List of classroom members could not be returned.";
		}
	}
	
	@Override
	public String getClassrooms() {
		List<Classroom> classrooms = userDao.getClassrooms();
		if (classrooms == null || classrooms.isEmpty()) {
			return "No classrooms found!";
		}
		try {
			ObjectMapper mapper = new ObjectMapper();
			String jsonString = mapper.writerWithView(MediaLibraryViews.RetriveUser.class).writeValueAsString(classrooms);	
			return jsonString;
		} catch (Exception e) {
			return "Internal error! List of classrooms could not be returned.";
		}
	}
	
	@Override
	public String getUsersInfo() {
		List<User> users = userDao.getUsersInfo();
		if (users == null || users.isEmpty()) {
			return "No users found!";
		}
		try {
			ObjectMapper mapper = new ObjectMapper();
			String jsonString = mapper.writerWithView(MediaLibraryViews.RetriveUsers.class).writeValueAsString(users);	
			return jsonString;
		} catch (Exception e) {
			return "Internal error! List of users could not be returned.";
		}
	}
	
	@Override
	public String getUser(String path) {

		if (path == null || path.length() == 0 || path.trim().equals("/")) {
			return "No user selected!";
		}
		Integer id = getId(path);
		if (id == null) {
			return "Invalid user ID!";
		}
		
		User user = userDao.getUserInfo(id);
		if (user == null) {
			return "No user with id " + id + " found!";
		}
		try {
			ObjectMapper mapper = new ObjectMapper();
			String jsonString = mapper.writerWithView(MediaLibraryViews.RetriveUser.class).writeValueAsString(user);	
			return jsonString;
		} catch (Exception e) {
			return "Internal error! User information could not be returned.";
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
	public String getUsersCurrentlyBooking() {
		List<User> users = userDao.getUsersCurrentlyBookingMaterials();
		if (users == null || users.isEmpty()) {
			return "No users found!";
		}
		try {
			ObjectMapper mapper = new ObjectMapper();
			String jsonString = mapper.writerWithView(MediaLibraryViews.RetriveUsers.class).writeValueAsString(users);	
			return jsonString;
		} catch (Exception e) {
			return "Internal error! List of users currently booking could not be returned.";
		}
	}
	
	@Override
	public String postAddUser(Map<String, String[]> postAddUser) {
		
		if (postAddUser == null || postAddUser.isEmpty()) {
			return "Something didn't went well! No form or an empty form received.";
		}
		
		User user = createUser(postAddUser);
		
		String validUser = validateUser(user);
		Boolean result = false;
		
		if (validUser == null || validUser.isEmpty()) {
			result = userDao.addUser(user);
		} else {
			return validUser;
		}
				
		if (result) {
			return "User successfuly added!";
		} else {
			return "User couldn't be added! Try again.";
		}
		
	}
	
	private User createUser(Map<String, String[]> postAddUser) {
		String userGroup[] = postAddUser.get("userGroup");
		String userFirstName[] = postAddUser.get("userFirstName");
		String userLastName[] = postAddUser.get("userLastName");
		String userUsername[] = postAddUser.get("userUsername");
		String userPassword[] = postAddUser.get("userPassword");
		
		int userGroupId=0;
		String value = "";
		UserGroup userGroupObj;
		switch(userGroup[0]) {
			case "student": userGroupObj = new UserGroup(1, "student");
						break;
			case "teacher": userGroupObj = new UserGroup(2, "teacher");
						break;
			case "manager": userGroupObj = new UserGroup(3, "manager");
						break;
			default: userGroupObj = null;
		}

		User user = new User(userFirstName[0], userLastName[0], userUsername[0], userPassword[0], userGroupObj);
		
		return user;
	}
	
	private String validateUser(User user) {
		String message = "The following required filds are missing or have invalid format: ";
		Boolean valid = true;
		if (user.getFirstName() == null || user.getFirstName().isEmpty()) {
			message = message + "no or invalid First Name, ";
			valid = false;
		}
		
		if (user.getLastName() == null || user.getLastName().isEmpty()) {
			message = message + "no or invalid Last Name, ";
			valid = false;
		}
		
		if (user.getUsername() == null || user.getUsername().isEmpty()) {
			message = message + "no or invalid username,  ";
			valid = false;
		}
		
		if (user.getPassword() == null || user.getPassword().isEmpty()) {
			message = message + "no or invalid password,  ";
			valid = false;
		}
		
		if (user.getUserGroup() == null) {
				message = message + "no or invalid user group, ";
				valid = false;
		}
		
		if (valid) {
			return "";
		}
		
		message = message.substring(0, message.length()-2) + ".";
		return message;
	}

}
