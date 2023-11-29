package servlet;

import static parameter.Messages.*;

import java.io.IOException;

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
 * Servlet implementation class CreateRoomServlet
 */
@WebServlet("/CreateRoomServlet")
public class CreateRoomServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CreateRoomServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String roomName = request.getParameter("roomName");
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		String userId = user.getUserId();
		boolean directed = false; 
		String privatedS = request.getParameter("privated");
		String roomId = "";
		Workspace workspace = (Workspace) session.getAttribute("workspace");
		String workspaceId = workspace.getWorkspaceID();
		
		//ダイレクトチャットの場合
		if(roomName == null) {
			String directUser = request.getParameter("userId");
			roomName = "P"+userId + "," + directUser;
			directed = true;
			privatedS = "private";
		}
		
		
		// パラメータチェック
		StringBuilder errorMsg = new StringBuilder();
		if (roomName == null || roomName.length() == 0) {
			errorMsg.append("ルーム名が入力されていません<br>");
		}
		if (privatedS == null || privatedS.length() == 0) {	
			errorMsg.append("可視性が選択されていません<br>");
		}
		if (errorMsg.length() > 0) {
			// エラー
			String nowRoomId = (String) session.getAttribute("nowRoomID");
			session.setAttribute("errorMsg", errorMsg.toString());
			session.setAttribute("errorType","createRoomeError");
			response.sendRedirect("MainServlet?roomId="+nowRoomId);
			return;
		}
		
		// privateをboolean型に変更
		boolean privatedB = false;
		if(privatedS.compareTo("private") == 0) {
			privatedB = true;
		}
		
		try {
			// Model → DAOにデータを私登録
			RoomModel roomModel = new RoomModel();
			roomId = roomModel.createRoom(roomName, userId,directed,privatedB,workspaceId);
			// TODO 同名エラー処理
			if(roomId == "error") {
				errorMsg = new StringBuilder();
				errorMsg.append("ルーム名またはユーザーIDに重複があります<br>");
				if (errorMsg.length() > 0) {
				String nowRoomId = (String) session.getAttribute("nowRoomID");
				session.setAttribute("errorMsg", errorMsg.toString());
				session.setAttribute("errorType","createRoomeError");
				response.sendRedirect("MainServlet?roomId="+nowRoomId);
				return;
				}
			}
			
		} catch (SwackException e) {
			e.printStackTrace();
			request.setAttribute("errorMsg", ERR_SYSTEM);
			request.getRequestDispatcher("/WEB-INF/jsp/main.jsp").forward(request, response);
			return;
		}
		
		session.setAttribute("createFlag", "True");
		//登録したルームIDをリクエストに登録しメイン画面へ遷移
		request.setAttribute("roomId",roomId );
		response.sendRedirect("MainServlet?roomId="+roomId);
	}

}
