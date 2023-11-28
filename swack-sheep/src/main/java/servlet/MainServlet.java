package servlet;

import static parameter.Messages.*;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.ChatLog;
import bean.Room;
import bean.SessionId;
import bean.User;
import bean.Workspace;
import bean.WorkspaceList;
import dao.WorkspaceDAO;
import exception.SwackException;
import model.ChatModel;
import model.RoomModel;
import model.SessionIdModel;
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
			String workspaceId = null;
			String workspaceName = null;
			String roomId = null;
			
			String cookieFlag = (String) session.getAttribute("cookieFlag");
			//セッションIdを使用したセッション管理
			//　保存されていた場合 userId.workspaceId,roomIdをDBから取得
			String sessionId = (String) session.getAttribute("sessionId");
			SessionIdModel sessionIdModel =  new SessionIdModel();
			
			if(cookieFlag.equals("1")){
				// DB接続
				SessionId sessionIds = sessionIdModel.getSessionId(sessionId);
				workspaceId = sessionIds.getWorkspaceId();
				roomId = sessionIdModel.getWorkspaceMemory(sessionIds);
//				session.setAttribute("cookieFlag","2");
				cookieFlag = "2";
			}
			
			//ユーザーの取得
			User user = (User) session.getAttribute("user");
					
			//ワークスペースの取得
			WorkspaceDAO workspaceDAO = new WorkspaceDAO();
			if(workspaceId == null) {
				workspaceId = request.getParameter("workspaceId");
				workspaceName = request.getParameter("workspaceName");
			}
			Workspace workspace = null;
			if(workspaceId == null) {
				workspace = (Workspace) session.getAttribute("workspace");
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
				workspaceName = workspaceDAO.getWorkspace(workspaceId);
			}else {
				//ワークスペースをセッションに保存
				workspace = new Workspace(workspaceId,user.getUserId());
				workspaceName = workspaceDAO.getWorkspace(workspaceId);
			}
			session.setAttribute("workspace",workspace);
			session.setAttribute("nowWorkspaceId", workspaceId);
			session.setAttribute("workspaceName", workspaceName);
			
			if(cookieFlag.equals("3")){ // ログイン後にワークスペースを変更した場合
				// DB接続
				Cookie[] cookies = request.getCookies();
				Cookie cookie = cookies[0];
				String sessionIdC = cookie.getValue();
				SessionId sessionIds = sessionIdModel.getSessionId(sessionIdC);
				sessionIds = new SessionId(user.getUserId(),workspaceId,sessionIds.getSessionId());
				roomId = sessionIdModel.getWorkspaceMemory(sessionIds);
				cookieFlag = "2";
			}
			
			//ルームの取得
			if(roomId == null) {
				roomId = request.getParameter("roomId");
			}
			//TODO ルームIDが保存されてない場合roomList.jspに遷移したい
			RoomModel roomModel = new RoomModel();
			if (roomId == null) {
//				request.getRequestDispatcher("/RoomListServlet").forward(request, response);
//				response.sendRedirect("/RoomListServlet");
//				roomId = "R0000";
				// TODO ワークスペースで最小のroomIdを持つルームを表示
				roomId = roomModel.getMinRoomId(workspaceId);
				
			}
			
			session.setAttribute("nowRoomID", roomId);
			
			// errorMsg取得＆セット
			String errorMsg = (String) session.getAttribute("errorMsg");
			String errorType = (String) session.getAttribute("errorType");
			session.removeAttribute("errorMsg");
			session.removeAttribute("errorType");
			if (errorMsg != null) {
				request.setAttribute("errorMsg", errorMsg);
				request.setAttribute("errorFlag",errorType);
				// TODO 11/24 エラータイプをフラグで管理・jsのonloadが動いていない
				// エラータイプでモーダル判定してメッセージ出力
				// モーダルウィンドウを閉じた際にエラー消去
			}
			
			// ルームを作成した後か判断
			String createFlag = (String) session.getAttribute("createFlag");
			session.removeAttribute("createFlag");
			if (createFlag != null) {
				request.setAttribute("roomCreateFlag", "True");
			}
			
			// ユーザー招待に必要な情報を取得
			roomModel = new RoomModel();
			List<User> userList = roomModel.getJoinUserList(roomId,user.getUserId(), workspaceId);//データベースからユーザーIDを取得
			StringBuilder userListErrorMsg = new StringBuilder();
			if (userList.size() == 0 || userList == null) {
				userListErrorMsg.append("招待可能なユーザーがいません");
			}	
			request.setAttribute("errorMsg", errorMsg);
			
			// TODO セッションIdをデータベースに保存 userId,workspaceId,roomId,sessionId
			sessionId = session.getId();
			Cookie cookie = new Cookie("sessionId",sessionId);
			cookie.setMaxAge(60 * 60 * 24); // cookie保存期間1日
			response.addCookie(cookie);
//			cookieFlag = (String) session.getAttribute("cookieFlag");
			SessionId nSessionId = new SessionId(user.getUserId(),workspaceId,sessionId);
			boolean insertFlag = false;
			if(cookieFlag.equals("1") || cookieFlag.equals("0")) { //セッション管理ID登録済みユーザか判別
				insertFlag = true;
				sessionIdModel.setSessionId(nSessionId,insertFlag);
				sessionIdModel.setWorkspaceMemory(nSessionId,roomId,insertFlag);
			}else {
				sessionIdModel.setSessionId(nSessionId,insertFlag);
				sessionIdModel.setWorkspaceMemory(nSessionId,roomId,insertFlag);
			}
			session.setAttribute("cookieFlag", "2");
			
			// TODO httponlyとsecure付けたい
		
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
