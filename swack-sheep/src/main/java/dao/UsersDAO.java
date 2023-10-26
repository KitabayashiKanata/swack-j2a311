package dao;

import static parameter.DAOParameters.*;
import static parameter.Messages.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import bean.User;
import exception.SwackException;

/**
 * ユーザ機能に関するDBアクセスを行う.
 */
public class UsersDAO {

	public User select(String mailAddress, String password) throws SwackException {
		// TODO SQL
		String sql = "SELECT USERID, USERNAME FROM USERS WHERE MAILADDRESS = ? AND PASSWORD = ?";

		User user = null;
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
			pStmt.setString(1, mailAddress);
			pStmt.setString(2, password);

			// SQL実行
			ResultSet rs = pStmt.executeQuery();

			// 結果を詰め替え
			if (rs.next()) {
				String userId = rs.getString("USERID");
				String userName = rs.getString("USERNAME");

				user = new User(userId, userName, mailAddress, "********");
			}

		} catch (SQLException e) {
			// エラー発生時、独自のExceptionを発行
			throw new SwackException(ERR_DB_PROCESS, e);
		}

		// 結果の返却（取得できなかった場合、nullが返却される）
		return user;
	}

	public boolean exists(String mailAddress) throws SwackException {
		// TODO SQL
		String sql = "********";

		// TODO Access DB

		return false;

	}

	public String selectMaxUserId() throws SwackException {
		// TODO SQL
		String sql = "********";

		String maxUserId = null;

		// TODO Access DB

		return maxUserId;

	}

	public boolean insert(User user) throws SwackException {
		// TODO SQL
		String sql = "********";

		// TODO Access DB

		return true;

	}
	
	public ArrayList<User> getUserList(String workspaceId,String userId) throws SwackException {
		// SQL
		String sql = "SELECT USERID,USERNAME FROM USERS WHERE USERID IN (SELECT USERID FROM JOINWORKSPACE WHERE WORKSPACEID = ? AND USERID <> ?)";

		ArrayList<User> userlist = new ArrayList<User>();

		// Access DB
		try (Connection conn = DriverManager.getConnection(DB_ENDPOINT, DB_USERID, DB_PASSWORD)) {

			// SQL作成
			PreparedStatement pStmt = conn.prepareStatement(sql);
			pStmt.setString(1, workspaceId);
			pStmt.setString(2, userId);

			// SQL実行
			ResultSet rs = pStmt.executeQuery();

			// 結果を詰め替え
			while (rs.next()) {
				String userIdRs = rs.getString("USERID");
				String userName = rs.getString("USERNAME");
				User user = new User(userIdRs,userName);

				userlist.add(user);
			}

		} catch (SQLException e) {
			throw new SwackException(ERR_DB_PROCESS, e);
		}

		return userlist;

	}

}
