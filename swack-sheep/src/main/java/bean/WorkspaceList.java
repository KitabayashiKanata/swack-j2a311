package bean;

public class WorkspaceList {
	/** ワークスペースID */
	private String workspaceID;
	/** ワークスペース名 **/
	private String workspaceName;
	
	public WorkspaceList(String workspaceID, String workspaceName) {
		this.workspaceID = workspaceID;
		this.workspaceName = workspaceName;
	}
	
	public String getWorkspaceID() {
		return workspaceID;
	}
	public String getWorkspaceName() {
		return workspaceName;
	}
}
