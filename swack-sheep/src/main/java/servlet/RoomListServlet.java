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

import bean.Room;
import bean.User;
import bean.Workspace;
import exception.SwackException;
import model.RoomModel;

/**
 * Servlet implementation class RoomListServlet
 */
@WebServlet("/RoomListServlet")
public class RoomListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RoomListServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
    	// TODO 最終的にはmain.jsp風に表示する形にしたい
		// 画面に必要な情報を準備する
		try {
			HttpSession session = request.getSession();
			Workspace workspace = (Workspace) session.getAttribute("workspace");
			String workspaceId = workspace.getWorkspaceID();
			
			User user = (User) session.getAttribute("user");
			String userId = user.getUserId();
			RoomModel roomModel = new RoomModel();
			List<Room> roomList = roomModel.getRoomList(workspaceId,userId);//データベースからユーザーIDを取得

			// JSPに値を渡す
			request.setAttribute("roomList", roomList);

		} catch (SwackException e) {
			e.printStackTrace();
			request.setAttribute("errorMsg", ERR_SYSTEM);
			request.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(request, response);
			return;

		}

		request.getRequestDispatcher("/WEB-INF/jsp/roomList.jsp").forward(request, response);
	}

}
