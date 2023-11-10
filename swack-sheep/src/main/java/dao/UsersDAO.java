package dao;

import static parameter.DAOParameters.*;
import static parameter.Messages.*;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
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
	
	// アカウントロック用ユーザーID取得
	public String getUserId(String mailAddress) throws SwackException {
		// TODO SQL
		String sql = "SELECT USERID FROM USERS WHERE MAILADDRESS = ?";

		String userId = null;
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
			pStmt.setString(1, mailAddress);

			// SQL実行
			ResultSet rs = pStmt.executeQuery();

			// 結果を詰め替え
			if (rs.next()) {
				userId = rs.getString("USERID");
			}

		} catch (SQLException e) {
			// エラー発生時、独自のExceptionを発行
			throw new SwackException(ERR_DB_PROCESS, e);
		}

		// 結果の返却（取得できなかった場合、nullが返却される）
		return userId;
	}

	public boolean exists(String mailAddress) throws SwackException {
		// SQL
		String sql = "SELECT COUNT(*) AS CNT FROM USERS WHERE MAILADDRESS = ?";

		int cnt = 0;
		// Access DB
		try (Connection conn = DriverManager.getConnection(DB_ENDPOINT, DB_USERID, DB_PASSWORD)) {

			// SQL作成
			PreparedStatement pStmt = conn.prepareStatement(sql);
			pStmt.setString(1, mailAddress);

			// SQL実行
			ResultSet rs = pStmt.executeQuery();

			// 結果を詰め替え
			if (rs.next()) {
				cnt = rs.getInt("CNT");
			}
			
			if (cnt == 1) {
				return false;
			}

		} catch (SQLException e) {
			throw new SwackException(ERR_DB_PROCESS, e);
		}
		return true;

	}

	public String selectMaxUserId() throws SwackException {
		// SQL
		String sql = "SELECT MAX(USERID) AS USERID FROM USERS";

		String maxUserId = null;
		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e1) {
			// TODO 自動生成された catch ブロック
			e1.printStackTrace();
		}

		// Access DB
		try (Connection conn = DriverManager.getConnection(DB_ENDPOINT, DB_USERID, DB_PASSWORD)) {

			// SQL作成
			PreparedStatement pStmt = conn.prepareStatement(sql);

			// SQL実行
			ResultSet rs = pStmt.executeQuery();

			// 結果を詰め替え
			if (rs.next()) {
				maxUserId = rs.getString("USERID");

			}
			
			// userIdを+１する
			String[] list = maxUserId.split("");
			String strId = "";
			strId = list[1] + list[2] + list[3] + list[4];
			int intId = Integer.valueOf(strId);
			intId += 1;
			strId = String.format("%04d", intId);
			maxUserId = list[0] + strId;

		} catch (SQLException e) {
			throw new SwackException(ERR_DB_PROCESS, e);
		}
		
		return maxUserId;

	}

	public boolean insert(User user) throws SwackException {
		// SQL
		String sql = "INSERT INTO USERS VALUES(?,?,?,?)";

		// Access DB
		try (Connection conn = DriverManager.getConnection(DB_ENDPOINT, DB_USERID, DB_PASSWORD)) {

			// SQL作成
			PreparedStatement pStmt = conn.prepareStatement(sql);
			pStmt.setString(1, user.getUserId());
			pStmt.setString(2, user.getUserName());
			pStmt.setString(3, user.getMailAddress());
			pStmt.setString(4, user.getPassword());

			// メールアドレスの重複確認
			UsersDAO usersDAO = new UsersDAO();
			boolean result = usersDAO.exists(user.getMailAddress());
			if (result) {
				// SQL実行
				pStmt.executeUpdate();
			}else {
				return false;
			}
			
		} catch (SQLException e) {
			throw new SwackException(ERR_DB_PROCESS, e);
		}
		return true;

	}
	
	public ArrayList<User> getUserList(String workspaceId,String userId) throws SwackException {
		// SQL
		String sql = "SELECT USERID,USERNAME FROM USERS "
				+ "WHERE USERID IN (SELECT USERID FROM JOINWORKSPACE WHERE WORKSPACEID = ? AND USERID <> ?)";

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
	
	public ArrayList<User> getJoinUserList(String roomId,String userId) throws SwackException {
		// SQL
		String sql = "SELECT USERID,USERNAME FROM USERS "
				+ "WHERE USERID NOT IN (SELECT USERID FROM JOINROOM WHERE ROOMID = ? AND USERID <> ?) AND USERID <> ?";

		ArrayList<User> userlist = new ArrayList<User>();
		//test

		// Access DB
		try (Connection conn = DriverManager.getConnection(DB_ENDPOINT, DB_USERID, DB_PASSWORD)) {

			// SQL作成
			PreparedStatement pStmt = conn.prepareStatement(sql);
			pStmt.setString(1, roomId);
			pStmt.setString(2, userId);
			pStmt.setString(3, userId);

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
	
	public ArrayList<User> getUserList(String workspaceId,ArrayList<User> userList) throws SwackException {
		// SQL
		String sql = "SELECT USERID,USERNAME FROM USERS "
				+ "WHERE USERID IN (SELECT USERID FROM JOINWORKSPACE WHERE WORKSPACEID = ? AND USERID = ?)";

		ArrayList<User> userlist = new ArrayList<User>();

		// Access DB
		try (Connection conn = DriverManager.getConnection(DB_ENDPOINT, DB_USERID, DB_PASSWORD)) {

			// SQL作成
			for (User u : userList) {
				
				PreparedStatement pStmt = conn.prepareStatement(sql);
				pStmt.setString(1, workspaceId);
				pStmt.setString(2, u.getUserId());
				
				// SQL実行
				ResultSet rs = pStmt.executeQuery();
	
				while (rs.next()) {
					// 結果を詰め替え
					String userIdRs = rs.getString("USERID");
					String userName = rs.getString("USERNAME");
					User user = new User(userIdRs,userName);
	
					userlist.add(user);
				}
			}
		} catch (SQLException e) {
			throw new SwackException(ERR_DB_PROCESS, e);
		}

		return userlist;

	}
	
	public void UpdatePassword(String userID, String newPassword) throws SwackException {
		String sql = "UPDATE USERS SET PASSWORD = ? WHERE USERID = ?";
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
			pStmt.setString(1, newPassword);
			pStmt.setString(2, userID);

			// SQL実行
			pStmt.executeUpdate();

		} catch (SQLException e) {
			// エラー発生時、独自のExceptionを発行
			throw new SwackException(ERR_DB_PROCESS, e);
		}
	}
	
	// アカウントロック操作
	public void lockUser(String userId, Timestamp daytime) throws SwackException{
		String sql = "INSERT INTO LOCKUSER VALUES(?, ?)";
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
			pStmt.setTimestamp(2, daytime);
			
			// SQL実行
			pStmt.executeUpdate();
		} catch (SQLException e) {
			// エラー発生時、独自のExceptionを発行
			throw new SwackException(ERR_DB_PROCESS, e);
		}
	}
	
	// アカウントロックの取得
	public boolean lockResult(String userId) throws SwackException{
		String sql = "SELECT DAYTIME FROM LOCKUSER WHERE USERID = ?";
		
		Date daytime = null;
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

			while (rs.next()) {
				// 結果を詰め替え
				daytime = rs.getDate("DAYTIME");
			}
			if (daytime == null) {
				return false;
			}
			return true;
		} catch (SQLException e) {
			// エラー発生時、独自のExceptionを発行
			throw new SwackException(ERR_DB_PROCESS, e);
		}
	}
	
	// アカウントロックされているユーザをすべて取得
	public ArrayList<User> getLockUser() throws SwackException{
		String sql = "SELECT T1.USERID AS USERID, USERNAME FROM LOCKUSER AS T1, USERS AS T2 WHERE T1.USERID = T2.USERID";
		
		ArrayList<User> userList = new ArrayList<User>();
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
			
			// SQL実行
			ResultSet rs = pStmt.executeQuery();

			while (rs.next()) {
				// 結果を詰め替え
				String userId = rs.getString("USERID");
				String userName = rs.getString("USERNAME");
				User user = new User(userId,userName);

				userList.add(user);
			}
			
			return userList;
		} catch (SQLException e) {
			// エラー発生時、独自のExceptionを発行
			throw new SwackException(ERR_DB_PROCESS, e);
		}
	}
	
	// アカウントロックの解除
	public void removeLockUser(String userId) throws SwackException{
		String sql = "DELETE FROM LOCKUSER WHERE USERID = ?";
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
			pStmt.executeUpdate();
		} catch (SQLException e) {
			// エラー発生時、独自のExceptionを発行
			throw new SwackException(ERR_DB_PROCESS, e);
		}
	}
	
	// 最終ログイン情報の保存
	public void loginUser(String userId, Date day) throws SwackException{
		String sql = "INSERT INTO LOGINUSER VALUES(?, ?)";
		
		// 既にあるログイン情報の削除
		UsersDAO usersDAO = new UsersDAO();
		usersDAO.removeloginUser(userId);
		
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
			pStmt.setDate(2, day);
			
			// SQL実行
			pStmt.executeUpdate();
		} catch (SQLException e) {
			// エラー発生時、独自のExceptionを発行
			throw new SwackException(ERR_DB_PROCESS, e);
		}
	}
	
	// 既にあるログイン情報の削除
	public void removeloginUser(String userId) throws SwackException{
		String sql = "DELETE FROM LOGINUSER WHERE USERID = ?";
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
			pStmt.executeUpdate();
		} catch (SQLException e) {
			// エラー発生時、独自のExceptionを発行
			throw new SwackException(ERR_DB_PROCESS, e);
		}
	}
	
	// 最終ログインの取得
	public Date getLoginDay(String userId) throws SwackException{
		String sql = "SELECT DAY FROM LOGINUSER WHERE USERID = ?";
		
		Date day = null;
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

			while (rs.next()) {
				// 結果を詰め替え
				day = rs.getDate("DAY");
			}
			return day;
		} catch (SQLException e) {
			// エラー発生時、独自のExceptionを発行
			throw new SwackException(ERR_DB_PROCESS, e);
		}
	}
}
