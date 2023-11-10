package servlet;

import static parameter.Messages.*;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.User;
import dao.UsersDAO;
import exception.SwackException;

/**
 * Servlet implementation class AdministratorServlet
 */
@WebServlet("/AdministratorServlet")
public class AdministratorServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public AdministratorServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 画面に必要な情報を準備する
		try {
			UsersDAO usersDAO = new UsersDAO();
			List<User> lockList = usersDAO.getLockUser();//アカウントロックされているユーザ一覧を取得

			// セッションに保存
			request.setAttribute("lockList", lockList);
			System.out.println(lockList);

		} catch (SwackException e) {
			e.printStackTrace();
			request.setAttribute("errorMsg", ERR_SYSTEM);
			request.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(request, response);
			return;

		}

		request.getRequestDispatcher("/WEB-INF/jsp/administrator.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
