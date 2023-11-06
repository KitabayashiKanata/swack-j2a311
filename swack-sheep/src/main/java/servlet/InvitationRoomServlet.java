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
import dao.RoomDAO;
import exception.SwackException;
import model.RoomModel;

@WebServlet("/InvitationRoomServlet")
public class InvitationRoomServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public InvitationRoomServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			// 情報の取得
			HttpSession session = request.getSession();
			String roomID = (String)session.getAttribute("nowRoomID");
			Workspace workspace = (Workspace) session.getAttribute("workspace");
			String workspaceID = workspace.getWorkspaceID();
			
			User user = (User) session.getAttribute("user");
			String userID = user.getUserId();
			RoomModel roomModel = new RoomModel();
			List<User> userList = roomModel.getJoinUserList(roomID,userID, workspaceID);//データベースからユーザーIDを取得
			
			// JSPに値を渡す
			request.setAttribute("userList", userList);
			
			// 招待対象が存在するかの確認
			StringBuilder errorMsg = new StringBuilder();
			if (userList.size() == 0 || userList == null) {
				errorMsg.append("招待できる人がいません");
			}
			request.setAttribute("errorMsg", errorMsg);

		} catch (SwackException e) {
			e.printStackTrace();
			request.setAttribute("errorMsg", ERR_SYSTEM);
			request.getRequestDispatcher("/WEB-INF/jsp/invitationRoom.jsp").forward(request, response);
			return;

		}
		request.getRequestDispatcher("/WEB-INF/jsp/invitationRoom.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			// UserIDとRoomIDを取得
			String userID = request.getParameter("userID");
			HttpSession session = request.getSession();
			String roomID = (String)session.getAttribute("nowRoomID");
			
			// joinroomテーブルにデータ追加
			RoomDAO roomDAO = new RoomDAO();
			roomDAO.insertJoinRoom(roomID, userID);
			
			// MainServlet
			response.sendRedirect("MainServlet?roomId=" + roomID);
			
		} catch (SwackException e) {
			e.printStackTrace();
			request.getRequestDispatcher("/WEB-INF/jsp/invitationRoom.jsp").forward(request, response);
			return;

		}
	}

}
