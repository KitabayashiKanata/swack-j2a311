package dao;

import static parameter.DAOParameters.*;
import static parameter.Messages.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.User;
import bean.Workspace;
import bean.WorkspaceList;
import exception.SwackException;

/**
 * ワークスペースに関するDBアクセスを行う
 */
public class WorkspaceDAO {
	public Workspace select(String userName, String mailAddress, String password) throws SwackException {
		// TODO SQL
		String sql = "SELECT USERID FROM USERS WHERE USERNAME = ? AND MAILADDRESS = ? AND PASSWORD = ?";

		Workspace workspace = null;
		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e1) {
			// TODO 自動生成された catch ブロック
			e1.printStackTrace();
		}
		
		// Access DB
		try (Connection conn = DriverManager.getConnection(DB_ENDPOINT, DB_USERID, DB_PASSWORD)) {

			// SQL作成
			PreparedStatement pStmt
			 = conn.prepareStatement(sql);
			pStmt.setString(1, userName);
			pStmt.setString(2, mailAddress);
			pStmt.setString(3, password);

			// SQL実行
			ResultSet rs = pStmt.executeQuery();

			// 結果を詰め替え
			if (rs.next()) {
				String userID = rs.getString("USERID");
				String workspaceID = new WorkspaceDAO().checkWorkspace(userID);
				workspace = new Workspace(workspaceID, userID);
				return workspace;
			}

		} catch (SQLException e) {
			// エラー発生時、独自のExceptionを発行
			throw new SwackException(ERR_DB_PROCESS, e);
		}

		// 結果の返却（取得できなかった場合、nullが返却される）
		return workspace;
	}
	
	// workspaceNameの取得
	public String getWorkspace(String workspaceId) throws SwackException{
		String sql = "SELECT WORKSPACENAME FROM WORKSPACE WHERE WORKSPACEID = ?";
		
		String workspaceName = null;
		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e1) {
			// TODO 自動生成された catch ブロック
			e1.printStackTrace();
		}
		// Access DB
		try (Connection conn = DriverManager.getConnection(DB_ENDPOINT, DB_USERID, DB_PASSWORD)) {

			// SQL作成
			PreparedStatement pStmt
			 = conn.prepareStatement(sql);
			pStmt.setString(1, workspaceId);

			// SQL実行
			ResultSet rs = pStmt.executeQuery();

			// 結果を詰め替え
			if (rs.next()) {
				workspaceName = rs.getString("WORKSPACENAME");
			}

		} catch (SQLException e) {
			// エラー発生時、独自のExceptionを発行
			throw new SwackException(ERR_DB_PROCESS, e);
		}
		
		// 結果の返却（取得できなかった場合、nullが返される）
		return workspaceName;
	}
	
	public String checkWorkspace(String userID) throws SwackException{
		// TODO SQL
		String sql = "SELECT WORKSPACEID FROM JOINWORKSPACE WHERE USERID = ?";

		String workspaceID = null;
		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e1) {
			// TODO 自動生成された catch ブロック
			e1.printStackTrace();
		}
		
		// Access DB
		try (Connection conn = DriverManager.getConnection(DB_ENDPOINT, DB_USERID, DB_PASSWORD)) {

			// SQL作成
			PreparedStatement pStmt
			 = conn.prepareStatement(sql);
			pStmt.setString(1, userID);

			// SQL実行
			ResultSet rs = pStmt.executeQuery();

			// 結果を詰め替え
			if (rs.next()) {
				workspaceID = rs.getString("WORKSPACEID");
			}

		} catch (SQLException e) {
			// エラー発生時、独自のExceptionを発行
			throw new SwackException(ERR_DB_PROCESS, e);
		}

		// 結果の返却（取得できなかった場合、nullが返される）
		return workspaceID;
	}
	
	public List<WorkspaceList> list(String userID) throws SwackException{
		// TODO SQL
		String sql = "SELECT WORKSPACEID, WORKSPACENAME FROM WORKSPACE WHERE WORKSPACEID IN (SELECT WORKSPACEID FROM JOINWORKSPACE WHERE USERID = ?)";
		
		List<WorkspaceList> workspace = new ArrayList<>();
		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e1) {
			// TODO 自動生成された catch ブロック
			e1.printStackTrace();
		}
		// Access DB
		try (Connection conn = DriverManager.getConnection(DB_ENDPOINT, DB_USERID, DB_PASSWORD)) {

			// SQL作成
			PreparedStatement pStmt
			 = conn.prepareStatement(sql);
			pStmt.setString(1, userID);

			// SQL実行
			ResultSet rs = pStmt.executeQuery();

			// 結果を詰め替え
			while (rs.next()) {
				String workspaceID = rs.getString("WORKSPACEID");
				String workspaceName = rs.getString("WORKSPACENAME");
				WorkspaceList workspaceList = new WorkspaceList(workspaceID, workspaceName);
				workspace.add(workspaceList);
			}

		} catch (SQLException e) {
			// エラー発生時、独自のExceptionを発行
			throw new SwackException(ERR_DB_PROCESS, e);
		}

		// 結果の返却（取得できなかった場合、nullが返される）
		return workspace;
	}
	
	// joinWorkspaceから特定のワークスペースに参加しているユーザをリストで取得
	public List<User> joinUser(String workspaceId) throws SwackException{
		String sql = "SELECT A.USERID AS USERID, USERNAME, MAILADDRESS FROM JOINWORKSPACE AS A, USERS AS B WHERE A.USERID = B.USERID AND WORKSPACEID = ?";
		
		List<User> joinUserList = new ArrayList<>();
		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e1) {
			// TODO 自動生成された catch ブロック
			e1.printStackTrace();
		}
		
		try (Connection conn = DriverManager.getConnection(DB_ENDPOINT, DB_USERID, DB_PASSWORD)) {
			// SQL作成
			PreparedStatement pStmt
			 = conn.prepareStatement(sql);
			pStmt.setString(1, workspaceId);

			// SQL実行
			ResultSet rs = pStmt.executeQuery();

			// 結果を詰め替え
			while (rs.next()) {
				String userId = rs.getString("USERID");
				String userName = rs.getString("USERNAME");
				String mailAddress = rs.getString("MAILADDRESS");
				User userList = new User(userId, userName, mailAddress, "******");
				joinUserList.add(userList);
						}
		} catch (SQLException e) {
			// エラー発生時、独自のExceptionを発行
			throw new SwackException(ERR_DB_PROCESS, e);
		}
		// 結果の返却（取得できなかった場合、nullが返される）
		return joinUserList;
	}
	
	// ワークスペースからの退会処理
	public void removeJoin(String userId, String workspaceId) throws SwackException{
		String sql = "DELETE FROM JOINWORKSPACE WHERE USERID = ? AND WORKSPACEID = ?";
		
		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e1) {
			// TODO 自動生成された catch ブロック
			e1.printStackTrace();
		}
		
		// Access DB
		try (Connection conn = DriverManager.getConnection(DB_ENDPOINT, DB_USERID, DB_PASSWORD)) {
			// SQL作成
			PreparedStatement pStmt
			 = conn.prepareStatement(sql);
			pStmt.setString(1, userId);
			pStmt.setString(2, workspaceId);
			
			// SQL実行
			pStmt.executeUpdate();
			
		} catch (SQLException e) {
			// エラー発生時、独自のExceptionを発行
			throw new SwackException(ERR_DB_PROCESS, e);
		}
	}
	
	// ワークスペースの管理者判定
	public ArrayList<Workspace> getAdmin(String userId) throws SwackException{
		String sql = "SELECT USERID, WORKSPACEID FROM WORKSPACEADMIN WHERE USERID = ?";
		ArrayList<Workspace> workspace = new ArrayList<>();
		
		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}
		
		// Access DB
		try (Connection conn = DriverManager.getConnection(DB_ENDPOINT, DB_USERID, DB_PASSWORD)) {
			
			// SQL作成
			PreparedStatement pStmt
			 = conn.prepareStatement(sql);
			pStmt.setString(1, userId);

			// SQL実行
			ResultSet rs = pStmt.executeQuery();

			// 結果を詰め替え
			while (rs.next()) {
				String workspaceID = rs.getString("WORKSPACEID");
				String userIdW = rs.getString("WORKSPACENAME");
				Workspace WadminUserList = new Workspace(workspaceID, userIdW);
				workspace.add(WadminUserList);
			}
			
			return workspace;
			
		} catch (SQLException e) {
			// エラー発生時、独自のExceptionを発行
			throw new SwackException(ERR_DB_PROCESS, e);
		}
	}
	
	// 指定ワークスペースの管理者を取得
	public ArrayList<String> getWorkspaceAdminList(String workspaceId) throws SwackException{
		ArrayList<String> workspaceList = new ArrayList<String>();
		
		String sql = "SELECT USERID FROM WORKSPACEADMIN WHERE WORKSPACEID = ?";
		
		try (Connection conn = DriverManager.getConnection(DB_ENDPOINT, DB_USERID, DB_PASSWORD)) {

			// SQL作成
			PreparedStatement pStmt = conn.prepareStatement(sql);
			pStmt.setString(1, workspaceId);

			// SQL実行
			ResultSet rs = pStmt.executeQuery();

			// 結果を詰め替え
			while (rs.next()) {
				String user = rs.getString("USERID");
				workspaceList.add(user);
			}
		} catch (SQLException e) {
			throw new SwackException(ERR_DB_PROCESS, e);
		}
		
		return workspaceList;
	}
	
	// あるユーザが特定のワークスペースの管理者か判定
	public boolean adminUserCheck(String userId, String workspaceId) throws SwackException {
		String sql = "SELECT COUNT(*) AS ADMIN FROM WORKSPACEADMIN WHERE USERID = ? AND WORKSPACEID = ?";
		
		int cnt = 0;
		
		try (Connection conn = DriverManager.getConnection(DB_ENDPOINT, DB_USERID, DB_PASSWORD)) {

			// SQL作成
			PreparedStatement pStmt = conn.prepareStatement(sql);
			pStmt.setString(1, userId);
			pStmt.setString(2, workspaceId);

			// SQL実行
			ResultSet rs = pStmt.executeQuery();

			// 結果を詰め替え
			while (rs.next()) {
				cnt = rs.getInt("ADMIN");
			}
			
			if (cnt == 0) {
				return false;
			}
		} catch (SQLException e) {
			throw new SwackException(ERR_DB_PROCESS, e);
		}
		
		return true;
	}
	
	// ワークスペースにユーザを追加
	public void insertJoinWorkspace(String userId , String workspaceId) throws SwackException {
		String sql = "INSERT INTO JOINWORKSPACE VALUES(?,?)";

		// Access DB
		try (Connection conn = DriverManager.getConnection(DB_ENDPOINT, DB_USERID, DB_PASSWORD)) {

			// SQL作成
			PreparedStatement pStmt = conn.prepareStatement(sql);
			pStmt.setString(1, workspaceId);
			pStmt.setString(2, userId);

			// SQL実行
			pStmt.executeUpdate();
		} catch (SQLException e) {
			throw new SwackException(ERR_DB_PROCESS, e);
		}
	}
}
