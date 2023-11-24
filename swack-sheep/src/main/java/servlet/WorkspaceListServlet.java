package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/WorkspaceListServlet")
public class WorkspaceListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public WorkspaceListServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		String cookieFlag = (String) session.getAttribute("cookieFlag");
		if(!cookieFlag.equals("0")) {
			session.setAttribute("cookieFlag", "3");
		}
		request.getRequestDispatcher("/WEB-INF/jsp/workspaceListResult.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

}
