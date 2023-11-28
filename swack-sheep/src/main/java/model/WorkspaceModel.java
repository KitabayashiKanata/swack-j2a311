package model;

import java.util.List;

import bean.Workspace;
import bean.WorkspaceList;
import dao.UsersDAO;
import dao.WorkspaceDAO;
import exception.SwackException;

public class WorkspaceModel {

	public Workspace checkWorkspace(String userName, String mailAddress, String password)throws SwackException {
		WorkspaceDAO workspaceDAO = new WorkspaceDAO();
		Workspace workspace = workspaceDAO.select(userName, mailAddress, password);
		return workspace;
	}
	
	public List<WorkspaceList> getWorkspaceList(String userId) throws SwackException{
		WorkspaceDAO workspaceDAO = new WorkspaceDAO();
		List<WorkspaceList> workspaceList = workspaceDAO.list(userId);
		return workspaceList;
	}
	
	public List<String> getWorkspaceAdminList(String workspaceId) throws SwackException{
		WorkspaceDAO workspaceDAO = new WorkspaceDAO();
		List<String> workspaceAdminList = workspaceDAO.getWorkspaceAdminList(workspaceId);
		return workspaceAdminList;
	}
	
	public boolean invitationMailAddressCheck(String mailAddress) throws SwackException{
		UsersDAO usersDAO = new UsersDAO();
		boolean judge = usersDAO.exists(mailAddress);
		return judge;
	}
	
	public String getUserId(String mailAddress) throws SwackException{
		UsersDAO usersDAO = new UsersDAO();
		String userId = usersDAO.getUserId(mailAddress);
		return userId;
	}
	
	public void insertJoinWorkspace(String userId,String workspaceId) throws SwackException{
		WorkspaceDAO workspaceDAO = new WorkspaceDAO();
		workspaceDAO.insertJoinWorkspace(userId, workspaceId);
	}
}
