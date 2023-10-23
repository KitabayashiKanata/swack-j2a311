package model;

import bean.Workspace;
import dao.WorkspaceDAO;
import exception.SwackException;

public class WorkspaceModel {

	public Workspace checkWorkspace(String userName, String mailAddress, String password)throws SwackException {
		WorkspaceDAO workspaceDAO = new WorkspaceDAO();
		Workspace workspace = workspaceDAO.select(userName, mailAddress, password);
		return workspace;
	}

}
