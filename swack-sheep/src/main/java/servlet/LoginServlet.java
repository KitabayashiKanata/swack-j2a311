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
import model.LoginModel;

/**
 * ログイン処理を実行するServlet
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// パラメータ取得
		String mailAddress = request.getParameter("mailAddress");
		String password = request.getParameter("password");

		// パラメータチェック
		StringBuilder errorMsg = new StringBuilder();
		if (mailAddress == null || mailAddress.length() == 0) {
			errorMsg.append("メールアドレスが入っていません<br>");
		}
		if (password == null || password.length() == 0) {
			errorMsg.append("パスワードが入っていません<br>");
		}
		if (errorMsg.length() > 0) {
			// エラー
			request.setAttribute("errorMsg", errorMsg.toString());
			request.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(request, response);
			return;
		}

		// 処理
		try {
			//ログインチェック
			User user = new LoginModel().checkLogin(mailAddress, password);
			if (user == null) {
				// 認証失敗
				request.setAttribute("errorMsg", ERR_LOGIN_PARAM_MISTAKE);
				request.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(request, response);
				return;
			} else {
				// 認証成功(ログイン情報をセッションに保持)
				HttpSession session = request.getSession();
				session.setAttribute("user", user);
				response.sendRedirect("MainServlet?roomId=R0000");
				return;
			}

		} catch (SwackException e) {
			e.printStackTrace();
			request.setAttribute("errorMsg", ERR_SYSTEM);
			request.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(request, response);
			return;
		}
	}

}