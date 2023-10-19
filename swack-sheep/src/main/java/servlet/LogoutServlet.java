package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
		request.getSession().invalidate();
		response.sendRedirect("LoginServlet");
	}

}
