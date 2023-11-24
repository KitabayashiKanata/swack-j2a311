package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.User;
import exception.SwackException;
import model.SessionIdModel;

/**
 * ログアウト処理を実行するServlet
 * 本Servletの実行にはログインを必要とする
 */
@WebServlet("/LogoutServlet")
public class LogoutServlet extends LoginCheckServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// セッションの破棄
		// sessionIdテーブルとworkspaceMemoryテーブルからデータの消去 userId使用
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		SessionIdModel sessionIdModel = new SessionIdModel();
		try {
			sessionIdModel.deleteSessionId(user.getUserId());
		} catch (SwackException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		
		request.getSession().invalidate();
		response.sendRedirect("LoginServlet");
	}

}
