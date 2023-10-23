package dao;

import static parameter.DAOParameters.*;
import static parameter.Messages.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
		String sql = "SELECT WORKSPECEID FROM USERS WHERE USERID = ?";

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
				workspaceID = rs.getString("WORKSPECEID");
			}

		} catch (SQLException e) {
			// エラー発生時、独自のExceptionを発行
			throw new SwackException(ERR_DB_PROCESS, e);
		}

		// 結果の返却（取得できなかった場合、nullが返される）
		return workspaceID;
	}
}
