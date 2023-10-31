package model;

import java.util.ArrayList;

import bean.Room;
import bean.User;
import dao.RoomDAO;
import dao.UsersDAO;
import exception.SwackException;

public class RoomModel {
	public String createRoom(String roomName,String createdUserId,boolean directed, boolean privated)throws SwackException {
		RoomDAO roomDAO = new RoomDAO();
		String roomId = roomDAO.createRoom(roomName, createdUserId,directed,privated);
		return roomId;
	}
	
	public String createRoom(String roomName,String createdUserId,boolean directed, boolean privated, String workspaceId)throws SwackException {
		RoomDAO roomDAO = new RoomDAO();
		String roomId = roomDAO.createRoom(roomName, createdUserId,directed,privated,workspaceId);
		return roomId;
	}
	
	public ArrayList<User> getUserList(String workspaceId,String userId) throws SwackException {
		UsersDAO userDAO = new UsersDAO();
		ArrayList<User> list
			= userDAO.getUserList(workspaceId,userId);
		return list;
	}
	
	public ArrayList<User> getJoinUserList(String roomId,String userId, String workspaceId) throws SwackException {
		UsersDAO userDAO = new UsersDAO();
		ArrayList<User> list = userDAO.getJoinUserList(roomId,userId);
		list = userDAO.getUserList(workspaceId, list);
		return list;
	}
	
	public ArrayList<Room> getRoomList(String workspaceId,String userId) throws SwackException{
		RoomDAO userDAO = new RoomDAO();
		ArrayList<Room> list
			= userDAO.getRoomList(workspaceId,userId);
		return list;
	}

}
