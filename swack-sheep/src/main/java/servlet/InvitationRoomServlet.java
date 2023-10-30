package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.RoomDAO;
import exception.SwackException;

@WebServlet("/InvitationRoomServlet")
public class InvitationRoomServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public InvitationRoomServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("/WEB-INF/jsp/test.jsp").forward(request, response);
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
			
			// main.jspに戻る
			request.getRequestDispatcher("/WEB-INF/jsp/main.jsp").forward(request, response);
			
		} catch (SwackException e) {
			e.printStackTrace();
			request.getRequestDispatcher("/WEB-INF/jsp/main.jsp").forward(request, response);
			return;

		}
	}

}
