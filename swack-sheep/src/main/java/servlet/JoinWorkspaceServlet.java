package servlet;

import static parameter.Messages.*;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			// パラメータの取得
			HttpSession session = request.getSession();
			Workspace workspace = (Workspace) session.getAttribute("workspace");
			String userId = request.getParameter("userId");
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
