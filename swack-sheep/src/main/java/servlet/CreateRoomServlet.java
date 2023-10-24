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
		//	TODO ダイレクトチャットの選択
		String roomName = request.getParameter("roomName");
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		String userId = user.getUserId();
		boolean directed = false; //ダイレクトチャット選択実装時に変更
		String privatedS = request.getParameter("privated");
		String roomId = "";
		
		
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
			request.setAttribute("errorMsg", errorMsg.toString());
			request.getRequestDispatcher("/WEB-INF/jsp/main.jsp").forward(request, response);
			return;
		}
		
		// privateをboolean型に変更
		boolean privatedB = false;
		if(privatedS == "private") {
			privatedB = true;
		}
		
		try {
			// Model → DAOにデータを私登録
			RoomModel roomModel = new RoomModel();
			roomId = roomModel.createRoom(roomName, userId,directed,privatedB);
			
		} catch (SwackException e) {
			e.printStackTrace();
			request.setAttribute("errorMsg", ERR_SYSTEM);
			request.getRequestDispatcher("/WEB-INF/jsp/main.jsp").forward(request, response);
			return;
		}
		
		//登録したルームIDをリクエストに登録しメイン画面へ遷移
		request.setAttribute("roomId",roomId );
		request.getRequestDispatcher("/WEB-INF/jsp/main.jsp").forward(request, response);
	}

}
