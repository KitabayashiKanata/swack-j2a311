package bean;

import java.io.Serializable;

/**
 * ルーム情報を管理するBean
 */
public class Room implements Serializable {
	private static final long serialVersionUID = 1L;

	/** ルームID */
	private String roomId;
	/** ルーム名 */
	private String roomName;
	/** 参加メンバー数 */
	private int memberCount;
	/** ダイレクトチャットか */
	private boolean directed;

	public Room() {
		// for JSP
	}

	public Room(String roomId, String roomName, int memberCount, boolean directed) {
		this.roomId = roomId;
		this.roomName = roomName;
		this.memberCount = memberCount;
		this.directed = directed;
	}

	public Room(String roomId, String roomName) {
		this.roomId = roomId;
		this.roomName = roomName;
		// 一覧表示用の場合は0人, falseで固定とする
		this.memberCount = 0;
		this.directed = false;
	}

	public String getRoomId() {
		return roomId;
	}

	public String getRoomName() {
		return roomName;
	}

	public int getMemberCount() {
		return memberCount;
	}

	public boolean isDirected() {
		return directed;
	}

	@Override
	public String toString() {
		return "Room [roomId=" + roomId + ", roomName=" + roomName + ", memberCount=" + memberCount + ", directed="
				+ directed + "]";
	}

}