package model;

import java.util.ArrayList;

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
	
	public ArrayList<User> getUserList(String workspaceId) throws SwackException {
		UsersDAO userDAO = new UsersDAO();
		ArrayList<User> list
			= userDAO.getUserList(workspaceId);
		return list;
	}

}
