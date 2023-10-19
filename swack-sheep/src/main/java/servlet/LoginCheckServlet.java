package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.User;

/**
 * 機能Servlet実行前に、ログイン状態を確認するServlet
 */
public class LoginCheckServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	public void service(ServletRequest req, ServletResponse res)
			throws ServletException, IOException {
		// チェックの準備
		HttpServletRequest request;
		HttpServletResponse response;

		try {
			request = (HttpServletRequest) req;
			response = (HttpServletResponse) res;

		} catch (ClassCastException e) {
			throw new ServletException("ClassCast Error at Login Check");

		}

		// ログインチェック
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("user");
		if (user != null) {
			// ログイン済の場合は、通常の処理を続行
			super.service(req, res);
		} else {
			// 未ログインの場合は、ログイン画面に遷移
			System.out.println("不正なアクセスを検知しました。ログイン画面へ遷移します。");
			response.sendRedirect("LoginServlet");
		}

	}

}
