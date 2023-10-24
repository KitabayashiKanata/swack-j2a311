package model;

import dao.RoomDAO;
import exception.SwackException;

public class RoomModel {
	public String createRoom(String roomName,String createdUserId,boolean directed, boolean privated)throws SwackException {
		RoomDAO roomDAO = new RoomDAO();
		String roomId = roomDAO.createRoom(roomName, createdUserId,directed,privated);
		return roomId;
	}

}
