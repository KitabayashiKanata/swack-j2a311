package servlet;

import static parameter.Messages.*;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.User;
import bean.Workspace;
import exception.SwackException;
import model.RoomModel;

/**
 * Servlet implementation class UserListServlet
 */
@WebServlet("/UserListServlet")
public class UserListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserListServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
//    protected void doGet(HttpServletRequest request, HttpServletResponse response)
//			throws ServletException, IOException {
//		request.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(request, response);
//	}

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		Workspace workspace = (Workspace) session.getAttribute("Workspace");

//		String workspaceId = workspace.getWorkspaceID();
		String workspaceId = "W0001";
		
		User user = (User) session.getAttribute("user");
		String userId = user.getUserId();
		// 画面に必要な情報を準備する
		try {
			RoomModel roomModel = new RoomModel();
			List<String> userList = roomModel.getUserList(workspaceId,userId);

			// JSPに値を渡す
			request.setAttribute("userList", userList);

		} catch (SwackException e) {
			e.printStackTrace();
			request.setAttribute("errorMsg", ERR_SYSTEM);
			request.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(request, response);
			return;

		}

		request.getRequestDispatcher("/WEB-INF/jsp/userList.jsp").forward(request, response);
	}

}
