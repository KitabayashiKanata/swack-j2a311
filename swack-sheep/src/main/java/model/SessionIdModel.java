package model;

import bean.SessionId;
import bean.User;
import dao.UsersDAO;
import exception.SwackException;

/**
 * セッションIDをデータベースで管理するクラス
 *
 */
public class SessionIdModel {
	
	public User connectSessionId(String sessionId) throws SwackException{
		UsersDAO usersDAO = new UsersDAO();
		User user = usersDAO.connectSessionId(sessionId);
		return user;
	}
	
	public void setSessionId(SessionId sessionId,boolean insertFlag) throws SwackException {
		UsersDAO usersDAO = new UsersDAO();
		usersDAO.setSessionId(sessionId,insertFlag);
	}
	
	
	public SessionId getSessionId(String sessionId) throws SwackException {
		UsersDAO usersDAO = new UsersDAO();
		SessionId sessionIds = usersDAO.getSessionId(sessionId);
		return sessionIds;
	}

	public void setWorkspaceMemory(SessionId sessionId,String roomId,boolean insertFlag) throws SwackException {
		UsersDAO usersDAO = new UsersDAO();
		usersDAO.setWorkspaceMemory(sessionId,roomId,insertFlag);
	}
	
	public String getWorkspaceMemory(SessionId sessionId)throws SwackException {
		UsersDAO usersDAO = new UsersDAO();
		String roomId = usersDAO.getWorkspaceMemory(sessionId);
		return roomId;
	}
	
	public void deleteSessionId(String userId)throws SwackException {
		UsersDAO usersDAO = new UsersDAO();
		usersDAO.deleteSessionId(userId);
		usersDAO.deleteWorkspaceMemory(userId);
	}
}
