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
	
	//workspaceid追加
	public ArrayList<Room> getRoomList(String userId,String workspaceId) throws SwackException {
		ChatDAO chatDAO = new ChatDAO();
		ArrayList<Room> list
			= chatDAO.getRoomList(userId,workspaceId);
		return list;
	}

	//workspaceid追加
	public ArrayList<Room> getDirectList(String userId,String workspaceId) throws SwackException {
		ChatDAO chatDAO = new ChatDAO();
		ArrayList<Room> list
			= chatDAO.getDirectList(userId,workspaceId);
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
	
	public void editMessage(int chatLogId,String newMessage,String userId) throws SwackException {
		ChatDAO chatDAO = new ChatDAO();
		chatDAO.editMessage(chatLogId,newMessage,userId);
	}
	
	public void deleteMessage(int chatLogId,String userId) throws SwackException {
		ChatDAO chatDAO = new ChatDAO();
		chatDAO.deleteMessage(chatLogId,userId);
	}

}
