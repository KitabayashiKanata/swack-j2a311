package servlet;

import static parameter.Messages.*;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.UsersDAO;
import exception.SwackException;

@WebServlet("/LockClearServlet")
public class LockClearServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public LockClearServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			// パラメータの取得
			String userId = request.getParameter("userId");
			
			// アカウントロックの解除
			UsersDAO usersDAO = new UsersDAO();
			usersDAO.removeLockUser(userId);
			
			// ???.jspへ戻る
			
			
		} catch (SwackException e) {
			e.printStackTrace();
			request.setAttribute("errorMsg", ERR_SYSTEM);
			request.getRequestDispatcher("/WEB-INF/jsp/???.jsp").forward(request, response);
			return;
		}
	}

}
