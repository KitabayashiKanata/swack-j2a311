package servlet;

import static parameter.Messages.*;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.User;
import bean.Workspace;
import dao.WorkspaceDAO;
import exception.SwackException;

@WebServlet("/JoinWorkspaceServlet")
public class JoinWorkspaceServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public JoinWorkspaceServlet() {
        super();
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			// jspに必要なデータをセッションに保存
			HttpSession session = request.getSession();
			WorkspaceDAO workspaceDAO = new WorkspaceDAO();
			
			String workspaceId = (String) session.getAttribute("nowWorkspaceId");
			
			String workspaceName = workspaceDAO.getWorkspace(workspaceId);
			session.setAttribute("workspaceName", workspaceName);
			
			ArrayList<User> joinUserList = (ArrayList<User>) workspaceDAO.joinUser(workspaceId);
			session.setAttribute("joinUserList", joinUserList);
			
			request.getRequestDispatcher("/WEB-INF/jsp/workspaceManager.jsp").forward(request, response);
		} catch (SwackException e) {
			// パラメータの取得
			HttpSession session = request.getSession();
			String roomId = (String) session.getAttribute("nowRoomID");
			
			e.printStackTrace();
			request.setAttribute("errorMsg", ERR_SYSTEM);
			
			// main.jspへ戻る
			response.sendRedirect("MainServlet?roomId=" + roomId);
			return;
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			// パラメータの取得
			HttpSession session = request.getSession();
			Workspace workspace = (Workspace) session.getAttribute("workspace");
			String userId = request.getParameter("removeUserId");
			String roomId = (String) session.getAttribute("nowRoomID");
			
			// joinWorkspaceからの削除
			WorkspaceDAO workspaceDAO = new WorkspaceDAO();
			workspaceDAO.removeJoin(userId, workspace.getWorkspaceID());
			
			// main.jspへ戻る
			response.sendRedirect("MainServlet?roomId=" + roomId);
			
		} catch (SwackException e) {
			// パラメータの取得
			HttpSession session = request.getSession();
			String roomId = (String) session.getAttribute("nowRoomID");
			
			e.printStackTrace();
			request.setAttribute("errorMsg", ERR_SYSTEM);
			
			// main.jspへ戻る
			response.sendRedirect("MainServlet?roomId=" + roomId);
			return;
		}
	}

}
