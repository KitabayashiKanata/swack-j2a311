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

import bean.ChatLog;
import bean.Room;
import bean.User;
import bean.Workspace;
import exception.SwackException;
import model.ChatModel;

/**
 * Servlet implementation class MainServlet
 */
@WebServlet("/MainServlet")
public class MainServlet extends LoginCheckServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public MainServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");

		String roomId = request.getParameter("roomId");
		if (roomId == null) {
			roomId = "R0000";
		}

		// 画面に必要な情報を準備する
		try {
			ChatModel chatModel = new ChatModel();
			Room room = chatModel.getRoom(roomId, user.getUserId());
			List<Room> roomList = chatModel.getRoomList(user.getUserId());
			List<Room> directList = chatModel.getDirectList(user.getUserId());
			List<ChatLog> chatLogList = chatModel.getChatlogList(roomId);
			Workspace workspace = (Workspace) session.getAttribute("workspace");
//			if (workspace == null) {
//				request.setAttribute("errorMsg", ERR_SYSTEM);
//				request.getRequestDispatcher("/WEB-INF/jsp/workspace.jsp").forward(request, response);
//				return;
//			}

			// JSPに値を渡す
			request.setAttribute("nowUser", user);
			request.setAttribute("nowRoom", room);
			request.setAttribute("roomList", roomList);
			request.setAttribute("directList", directList);
			request.setAttribute("chatLogList", chatLogList);

		} catch (SwackException e) {
			e.printStackTrace();
			request.setAttribute("errorMsg", ERR_SYSTEM);
			request.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(request, response);
			return;

		}

		request.getRequestDispatcher("/WEB-INF/jsp/main.jsp").forward(request, response);
	}

}
