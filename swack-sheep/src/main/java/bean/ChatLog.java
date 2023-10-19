package bean;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * ルームチャットログ情報を管理するBean
 */
public class ChatLog implements Serializable {
	private static final long serialVersionUID = 1L;

	/** チャットログID */
	private int chatLogId;
	/** ルームID */
	private String roomId;
	/** ユーザID */
	private String userId;
	/** ユーザ名 */
	private String userName;
	/** メッセージ */
	private String message;
	/** 投稿日時 */
	private Timestamp createdAt;

	public ChatLog() {
		// for JSP
	}

	public ChatLog(int chatLogId, String roomId, String userId, String userName, String message,
			Timestamp createdAt) {
		this.chatLogId = chatLogId;
		this.roomId = roomId;
		this.userId = userId;
		this.userName = userName;
		this.message = message;
		this.createdAt = createdAt;
	}

	public int getChatLogId() {
		return chatLogId;
	}

	public String getRoomId() {
		return roomId;
	}

	public String getUserId() {
		return userId;
	}

	public String getUserName() {
		return userName;
	}

	public String getMessage() {
		return message;
	}

	public Timestamp getCreatedAt() {
		return createdAt;
	}

	@Override
	public String toString() {
		return "ChatLog [chatLogId=" + chatLogId + ", roomId=" + roomId + ", userId=" + userId + ", userName="
				+ userName + ", message=" + message + ", createdAt=" + createdAt + "]";
	}

}
