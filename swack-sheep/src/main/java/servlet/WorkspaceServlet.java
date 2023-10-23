package servlet;

import static parameter.Messages.*;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.Workspace;
import exception.SwackException;
import model.WorkspaceModel;

/**
 * Servlet implementation class WorkspaceServlet
 */
@WebServlet("/WorkspaceServlet")
public class WorkspaceServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public WorkspaceServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.getRequestDispatcher("/WEB-INF/jsp/workspace.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// パラメータ取得
		String userName = request.getParameter("userName");
		String mailAddress = request.getParameter("mailAddress");
		String password = request.getParameter("password");
		
		
		// パラメータチェック
		StringBuilder errorMsg = new StringBuilder();
		if (userName == null || userName.length() == 0) {
			errorMsg.append("氏名が入っていません<br>");
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
			request.getRequestDispatcher("/WEB-INF/jsp/workspace.jsp").forward(request, response);
			return;
		}
		
		// 処理
		try {
			//ログインチェック
			Workspace workspace = new WorkspaceModel().checkWorkspace(userName, mailAddress, password);
			if (workspace == null) {
				// 認証失敗
				request.setAttribute("errorMsg", ERR_LOGIN_PARAM_MISTAKE);
				request.getRequestDispatcher("/WEB-INF/jsp/workspace.jsp").forward(request, response);
				return;
			}else {
				request.setAttribute("workspace", workspace);
				return;
			}
			


		} catch (SwackException e) {
			e.printStackTrace();
			request.setAttribute("errorMsg", ERR_SYSTEM);
			request.getRequestDispatcher("/WEB-INF/jsp/workspace.jsp").forward(request, response);
			return;
		}
	}

}
