package bean;

import java.io.Serializable;

/**
 * セッションIDを管理するクラス
 */
public class SessionId implements Serializable{
	private static final long serialVersionUID = 1L;

	/** ユーザID */
	private String userId;
	/** ワークスペースID*/
	private String workspaceId;
	/** ルームID */
//	private String roomId;
	/** セッションID */
	private String sessionId;
	
	public SessionId(String userId,String workspaceId,String sessionId) {
		this.userId = userId;
		this.workspaceId = workspaceId;
//		this.roomId = roomId;
		this.sessionId = sessionId;
	}
	
	public String getUserId() {
		return userId;
	}
	public String getWorkspaceId() {
		return workspaceId;
	}
//	public String getRoomId() {
//		return roomId;
//	}
	public String getSessionId() {
		return sessionId;
	}
	
		
}
