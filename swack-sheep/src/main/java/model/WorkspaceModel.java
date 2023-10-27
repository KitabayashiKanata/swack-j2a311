package model;

import java.util.List;

import bean.Workspace;
import bean.WorkspaceList;
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

}
