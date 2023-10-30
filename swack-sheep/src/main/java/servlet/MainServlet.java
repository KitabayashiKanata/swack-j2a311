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
import bean.WorkspaceList;
import exception.SwackException;
import model.ChatModel;
import model.WorkspaceModel;

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
		try {
			HttpSession session = request.getSession();
			
			//ユーザーの取得
			User user = (User) session.getAttribute("user");
					
			//ワークスペースの取得
			String workspaceId = request.getParameter("workspaceId");
			if(workspaceId == null) {
				Workspace workspace = (Workspace) session.getAttribute("workspace");
				if (workspace == null) {
					request.setAttribute("errorMsg", ERR_SYSTEM);
					request.setAttribute("nowUser", user);
					WorkspaceModel workspaceModel = new WorkspaceModel();
					List<WorkspaceList> workspaceList = workspaceModel.getWorkspaceList(user.getUserId());
					request.setAttribute("workspaceList", workspaceList);
					request.getRequestDispatcher("/WEB-INF/jsp/workspaceList.jsp").forward(request, response);
					return;
				}
				workspaceId = workspace.getWorkspaceID();
			}else {
				//ワークスペースをセッションに保存
				Workspace workspace = new Workspace(workspaceId,user.getUserId());
				session.setAttribute("workspace",workspace);
			}
			
			
			
			//ルームの取得
			String roomId = request.getParameter("roomId");
			//TODO ルームIDが保存されてない場合roomList.jspに遷移したい
			if (roomId == null) {
				request.getRequestDispatcher("/RoomListServlet").forward(request, response);
//				response.sendRedirect("/RoomListServlet");
				roomId = "R0000";
			}
			
			session.setAttribute("nowRoomID", roomId);

		// 画面に必要な情報を準備する
		
			ChatModel chatModel = new ChatModel();
			Room room = chatModel.getRoom(roomId, user.getUserId());
			List<Room> roomList = chatModel.getRoomList(user.getUserId(),workspaceId); //workspaceIdを追加
			List<Room> directList = chatModel.getDirectList(user.getUserId(),workspaceId); //workspaceIdを追加
			List<ChatLog> chatLogList = chatModel.getChatlogList(roomId);
			

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
