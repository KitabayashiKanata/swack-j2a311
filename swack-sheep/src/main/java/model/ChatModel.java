package model;

import java.util.ArrayList;
import java.util.List;

import bean.ChatLog;
import bean.Room;
import dao.ChatDAO;
import exception.SwackException;

/**
 * チャット機能を実行するクラス
 */
public class ChatModel {

	public Room getRoom(String roomId, String userId) throws SwackException {
		ChatDAO chatDAO = new ChatDAO();
		Room room = chatDAO.getRoom(roomId, userId);
		return room;

	}

	public ArrayList<Room> getRoomList(String userId) throws SwackException {
		ChatDAO chatDAO = new ChatDAO();
		ArrayList<Room> list
			= chatDAO.getRoomList(userId);
		return list;
	}

	public ArrayList<Room> getDirectList(String userId) throws SwackException {
		ChatDAO chatDAO = new ChatDAO();
		ArrayList<Room> list
			= chatDAO.getDirectList(userId);
		return list;
	}

	public List<ChatLog> getChatlogList(String roomId) throws SwackException {
		ChatDAO chatDAO = new ChatDAO();
		List<ChatLog> list = chatDAO.getChatlogList(roomId);
		return list;
	}
	public void saveChatLog(String roomId, String userId, String message) throws SwackException {
		new ChatDAO().saveChatlog(roomId, userId, message);
	}

}
