package servlet;

import static parameter.Messages.*;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.User;
import dao.UsersDAO;
import exception.SwackException;

@WebServlet("/CreateUserServlet")
public class CreateUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public CreateUserServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("/WEB-INF/jsp/signup.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			// パラメータ取得
			String userName = request.getParameter("userName");
			String mailAddress = request.getParameter("mailAddress");
			String password = request.getParameter("password");
			String password2 = request.getParameter("password2");
	
			// パラメータチェック
			StringBuilder errorMsg = new StringBuilder();
			if (!password.equals(password2)) {
				errorMsg.append("入力したパスワードが確認用パスワードと不一致です");
				request.setAttribute("errorMsg", errorMsg.toString());
				request.getRequestDispatcher("/WEB-INF/jsp/signup.jsp").forward(request, response);
			}
			if (userName == null || userName.length() == 0) {
				errorMsg.append("ユーザー名が入っていません<br>");
			}
			if (mailAddress == null || mailAddress.length() == 0) {
				errorMsg.append("メールアドレスが入っていません<br>");
			}
			if (password == null || password.length() == 0) {
				errorMsg.append("パスワードが入っていません<br>");
			}
			if (errorMsg.length() > 0) {
				// エラー
				request.setAttribute("errorMsg", errorMsg.toString());
				request.getRequestDispatcher("/WEB-INF/jsp/signup.jsp").forward(request, response);
				return;
			}
			
			// 既に設定されているuserIDの最大値+1を出力
			UsersDAO usersDAO = new UsersDAO();
			String userID = usersDAO.selectMaxUserId();
			
			// user情報をDBに追加
			User user = new User(userID,userName,mailAddress,password);
			boolean result = usersDAO.insert(user);
			
			if (result == false) {
				// メールアドレスの重複あり
				errorMsg.append("そのメールアドレスは既に登録されています");
				request.setAttribute("errorMsg", errorMsg.toString());
				request.getRequestDispatcher("/WEB-INF/jsp/signup.jsp").forward(request, response);
			}
			
		} catch (SwackException e) {
			e.printStackTrace();
			request.setAttribute("errorMsg", ERR_SYSTEM);
			request.getRequestDispatcher("/WEB-INF/jsp/signup.jsp").forward(request, response);
			return;

		}
		
		request.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(request, response);
	}

}
