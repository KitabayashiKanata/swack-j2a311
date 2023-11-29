package servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Workspace;
import bean.WorkspaceList;
import exception.SwackException;
import model.RoomModel;
import model.WorkspaceModel;

/**
 * Servlet implementation class InvitationWorkspaceServlet
 */
@WebServlet("/InvitationWorkspaceServlet")
public class InvitationWorkspaceServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public InvitationWorkspaceServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		String nowRoomId = (String) session.getAttribute("nowRoomID");
		Workspace workspace =  (Workspace) session.getAttribute("workspace");
		String workspaceId = workspace.getWorkspaceID();
		List<WorkspaceList> checkWorkspaceList = null;
		String mailaddress = "";
		String userId = "";
		String minRoomId = "";
		boolean judge = true;
		boolean idFlag = false;
		mailaddress = request.getParameter("invitationMailaddress");
		WorkspaceModel workspaceModel = new WorkspaceModel();
		RoomModel roomModel = new RoomModel();
		StringBuilder errorMsg = new StringBuilder();
		try {
			judge = workspaceModel.invitationMailAddressCheck(mailaddress); //当該メールアドレスのユーザが存在するか
			if(!judge) {
				userId = workspaceModel.getUserId(mailaddress); // メールアドレスからユーザIDを取得
				checkWorkspaceList = workspaceModel.getWorkspaceList(userId);//userがワークスペースに現在参加しているかチェック
				for(int i = 0; i<checkWorkspaceList.size();i++) {
					if(workspaceId.equals(checkWorkspaceList.get(i).getWorkspaceID())) {
						idFlag = true;
						errorMsg.append("当該ユーザーはすでにワークスペースに参加しています<br>");
					}
				}
				if(!idFlag) {
					workspaceModel.insertJoinWorkspace(userId,workspaceId); //joinworkspaceテーブルに追加
					// workspace最小のIDを持つテーブルにjoinする
					minRoomId = roomModel.getMinRoomId(workspaceId);
					roomModel.insertJoinRoom(minRoomId, userId);
				}
			}else {
				errorMsg.append("当該メールアドレスのユーザーは存在しません<br>");
			}
		} catch (SwackException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		if (errorMsg.length() > 0) {
			// エラー
			session.setAttribute("errorMsg", errorMsg.toString());
			session.setAttribute("errorType","invitationWorkspaceError");
			response.sendRedirect("MainServlet?roomId="+nowRoomId);
			return;
		}
		response.sendRedirect("MainServlet?roomId="+nowRoomId);
	}
}
