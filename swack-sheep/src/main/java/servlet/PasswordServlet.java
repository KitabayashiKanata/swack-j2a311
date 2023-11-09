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
import dao.UsersDAO;
import exception.SwackException;

@WebServlet("/PasswordServlet")
public class PasswordServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public PasswordServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("/WEB-INF/jsp/changePassword.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// パラメータ取得
		String newPassword1 = request.getParameter("newPassword1");
		String newPassword2 = request.getParameter("newPassword2");
		
		// パラメータチェック
		StringBuilder errorMsg = new StringBuilder();
		if (!newPassword1.equals(newPassword2)) {
			errorMsg.append("入力したパスワードが確認用パスワードと不一致です");
			request.setAttribute("errorMsg", errorMsg.toString());
			request.getRequestDispatcher("/WEB-INF/jsp/changePassword.jsp").forward(request, response);
		}
		if (newPassword1 == null || newPassword1.length() == 0) {
			errorMsg.append("新しいパスワードが入っていません<br>");
		}
		if (newPassword2 == null || newPassword2.length() == 0) {
			errorMsg.append("確認用パスワードが入っていません<br>");
		}
		if (errorMsg.length() > 0) {
			// エラー
			request.setAttribute("errorMsg", errorMsg.toString());
			request.getRequestDispatcher("/WEB-INF/jsp/changePassword.jsp").forward(request, response);
			return;
		}
		
		// 処理
		try {
			HttpSession session = request.getSession();
			User user = (User) session.getAttribute("user");
			String roomId = (String) session.getAttribute("nowRoomID");
			
			UsersDAO usersDAO = new UsersDAO();
			usersDAO.UpdatePassword(user.getUserId(), newPassword1);
			response.sendRedirect("MainServlet?roomId=" + roomId);
		} catch (SwackException e) {
			e.printStackTrace();
			request.setAttribute("errorMsg", ERR_SYSTEM);
			request.getRequestDispatcher("/WEB-INF/jsp/changePassword.jsp").forward(request, response);
			return;
		}
	}

}
