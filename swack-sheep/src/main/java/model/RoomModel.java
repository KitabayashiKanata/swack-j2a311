package model;

import java.util.ArrayList;

import dao.RoomDAO;
import dao.UsersDAO;
import exception.SwackException;

public class RoomModel {
	public String createRoom(String roomName,String createdUserId,boolean directed, boolean privated)throws SwackException {
		RoomDAO roomDAO = new RoomDAO();
		String roomId = roomDAO.createRoom(roomName, createdUserId,directed,privated);
		return roomId;
	}
	
	public ArrayList<String> getUserList(String workspaceId,String userId) throws SwackException {
		UsersDAO userDAO = new UsersDAO();
		ArrayList<String> list
			= userDAO.getUserList(workspaceId,userId);
		return list;
	}

}
