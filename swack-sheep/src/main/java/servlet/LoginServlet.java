package servlet;

import static parameter.Messages.*;

import java.io.IOException;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.User;
import dao.UsersDAO;
import exception.SwackException;
import model.LoginModel;
import model.TimeModel;

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
		HttpSession session = request.getSession();
		String sCnt = (String) session.getAttribute("logCnt");
		int cnt;
		
		if (sCnt == null || sCnt.length() == 0) {
			cnt = 0;
		}else {
			cnt = Integer.parseInt(sCnt);
		}
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
			// userIdの取得
			UsersDAO usersDAO = new UsersDAO();
			String userId = usersDAO.getUserId(mailAddress);
			
			// アカウントロック判定
			boolean lock = usersDAO.getLockUser(userId);
			if (lock) {
				// セッションのリセット
				session.setAttribute("logCnt", "");
				String err = "アカウントがロックされています";
				request.setAttribute("errorMsg", err);
				request.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(request, response);
				return;
			}
			 
			//現在日時を取得
			Timestamp now = new Timestamp(System.currentTimeMillis());
	        
	        // 表示形式を指定
	        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String datetime = df.format(now);
			Timestamp timestamp = Timestamp.valueOf(datetime);
			
			// 3週間以内にログインがあったか確認
			var day = usersDAO.getLoginDay(userId);
			var nowDay = new Date(timestamp.getTime());
			boolean time_flg = false;
			
			if (day != null) {
				time_flg = new TimeModel().DayChack(nowDay, day);
			}
			
			if (time_flg) {
				// アカウントロック操作
				usersDAO.lockUser(userId, timestamp);
				String err = "3週間以内にログインが無かったため<br>アカウントがロックされました";
				request.setAttribute("errorMsg", err);
				request.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(request, response);
				return;
			}
			
			//ログインチェック
			User user = new LoginModel().checkLogin(mailAddress, password);
			
			if (user == null) {
				// 認証失敗
				cnt += 1;
				session.setAttribute("logCnt", String.valueOf(cnt));
				
				if (cnt >= 5) {
					// セッションのリセット
					session.setAttribute("logCnt", "");
					
					// アカウントロック操作
					usersDAO.lockUser(userId, timestamp);
					String err = "ログインに5回失敗したため<br>アカウントがロックされました";
					request.setAttribute("errorMsg", err);
					request.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(request, response);
				}
				
				
				request.setAttribute("errorMsg", ERR_LOGIN_PARAM_MISTAKE);
				request.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(request, response);
				return;
			} else {
				// 管理者判定
				
				
				// 最終ログイン情報の保存
				SimpleDateFormat d = new SimpleDateFormat("yyyy-MM-dd");
				String date = d.format(now);
				Date nowdate = Date.valueOf(date);
				usersDAO.loginUser(userId, nowdate);
				
				// 認証成功(ログイン情報をセッションに保持)
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