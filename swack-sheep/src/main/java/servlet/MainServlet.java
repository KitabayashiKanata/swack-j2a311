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
import model.RoomModel;
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
//				request.getRequestDispatcher("/RoomListServlet").forward(request, response);
//				response.sendRedirect("/RoomListServlet");
				roomId = "R0000";
			}
			
			session.setAttribute("nowRoomID", roomId);
			
			// errorMsg取得＆セット
			String errorMsg = (String) session.getAttribute("errorMsg");
			session.removeAttribute("errorMsg");
			if (errorMsg != null) {
				request.setAttribute("errorMsg", errorMsg);
			}
			
			// ルームを作成した後か判断
			String createFlag = (String) session.getAttribute("createFlag");
			session.removeAttribute("createFlag");
			if (createFlag != null) {
				request.setAttribute("createFlag", "True");
			}
			
			// ユーザー招待に必要な情報を取得
			RoomModel roomModel = new RoomModel();
			List<User> userList = roomModel.getJoinUserList(roomId,user.getUserId(), workspaceId);//データベースからユーザーIDを取得
			StringBuilder userListErrorMsg = new StringBuilder();
			if (userList.size() == 0 || userList == null) {
				userListErrorMsg.append("招待可能なユーザーがいません");
			}
			request.setAttribute("errorMsg", errorMsg);
		
			// 画面に必要な情報を準備する
		
			ChatModel chatModel = new ChatModel();
//			RoomModel roomModel = new RoomModel();
			WorkspaceModel workspaceModel = new WorkspaceModel();
			Room room = chatModel.getRoom(roomId, user.getUserId());
			List<Room> roomList = chatModel.getRoomList(user.getUserId(),workspaceId); //workspaceIdを追加
			List<Room> directList = chatModel.getDirectList(user.getUserId(),workspaceId); //workspaceIdを追加
			List<ChatLog> chatLogList = chatModel.getChatlogList(roomId);
			List<String> roomAdminList = roomModel.getRoomAdminList(roomId);
			List<String> workspaceAdminList = workspaceModel.getWorkspaceAdminList(workspaceId);
			

			// JSPに値を渡す
			request.setAttribute("nowUser", user);
			request.setAttribute("nowRoom", room);
			request.setAttribute("roomList", roomList);
			request.setAttribute("directList", directList);
			request.setAttribute("chatLogList", chatLogList);
			request.setAttribute("roomAdminList", roomAdminList);
			request.setAttribute("workspaceAdminList", workspaceAdminList);
			request.setAttribute("userList", userList);

		} catch (SwackException e) {
			e.printStackTrace();
			request.setAttribute("errorMsg", ERR_SYSTEM);
			request.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(request, response);
			return;

		}

		request.getRequestDispatcher("/WEB-INF/jsp/main.jsp").forward(request, response);
	}

}
