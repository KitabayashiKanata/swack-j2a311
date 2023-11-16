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
	/** ワークスペースID（追加）*/
	private String workspaceId;
	/** プライベートか（追加） */
	private boolean privated;
	
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
	
	//workspaceId追加
	public Room(String roomId, String roomName,String workspaceId) {
		this.roomId = roomId;
		this.roomName = roomName;
		// 一覧表示用の場合は0人, falseで固定とする
		this.memberCount = 0;
		this.directed = false;
		this.workspaceId = workspaceId;
	}

	public Room(String roomId, String roomName, boolean privated, String workspaceId) {
		this.roomId = roomId;
		this.roomName = roomName;
		this.privated = privated;
		this.workspaceId = workspaceId;
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

	public String getWorkspaceId() {
		return workspaceId;
	}
	
	public boolean isPrivated() {
		return privated;
	}

	@Override
	public String toString() {
		return "Room [roomId=" + roomId + ", roomName=" + roomName + ", memberCount=" + memberCount + ", directed="
				+ directed + ",privated=" + privated +"]";
	}

}