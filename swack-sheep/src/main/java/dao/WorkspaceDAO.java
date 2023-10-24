package dao;

import static parameter.DAOParameters.*;
import static parameter.Messages.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import bean.Workspace;
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
	
	public List<String> list(String userID) throws SwackException{
		// TODO SQL
		String sql = "SELECT WORKSPACENAME FROM WORKSPACE WHERE WORKSPACEID IN (SELECT WORKSPACEID FROM JOINWORKSPACE WHERE USERID = ?)";
		
		List<String> workspaceList = null;
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
				String workspace = rs.getString("WORKSPACEID");
				workspaceList.add(workspace);
			}

		} catch (SQLException e) {
			// エラー発生時、独自のExceptionを発行
			throw new SwackException(ERR_DB_PROCESS, e);
		}

		// 結果の返却（取得できなかった場合、nullが返される）
		return workspaceList;
	}
}
