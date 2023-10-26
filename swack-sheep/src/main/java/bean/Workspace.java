package bean;

import java.io.Serializable;

/**
 * ワークスペース情報を管理するBean
 */
public class Workspace implements Serializable{
	private static final long serialVersionUID = 1L;
	
	/** ユーザーID **/
	private String userID;
	/** ワークスペースID */
	private String workspaceID;
	
	public Workspace() {
	}
	
	public Workspace(String workspaceID, String userID) {
		this.workspaceID = workspaceID;
		this.userID = userID;
	}
	
	public String getWorkspaceID() {
		return workspaceID;
	}
	public String getUserID() {
		return userID;
	}
	
}
