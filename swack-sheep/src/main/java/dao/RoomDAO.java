package dao;

import static parameter.DAOParameters.*;
import static parameter.Messages.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import bean.Room;
import exception.SwackException;

public class RoomDAO {
	
	public String createRoom(String roomName,String createdUserId,boolean directed, boolean privated)throws SwackException{
		String roomId = getMaxRoomId();
		
		
		String sql = "INSERT INTO ROOMS VALUES(?,?,?,?,?)";
		try (Connection conn = DriverManager.getConnection(DB_ENDPOINT, DB_USERID, DB_PASSWORD)) {
			PreparedStatement pStmt = conn.prepareStatement(sql);
			pStmt.setString(1, roomId);
			pStmt.setString(2, roomName);
			pStmt.setString(3, createdUserId);
			pStmt.setBoolean(4, directed);
			pStmt.setBoolean(5, privated);
			

			pStmt.executeUpdate();
		}catch (SQLException e) {
			throw new SwackException(ERR_DB_PROCESS, e);
		}
		
		insertJoinRoom(roomId,createdUserId);
		return roomId;
	}
	
	//workspaceid追加
	public String createRoom(String roomName,String createdUserId,boolean directed, boolean privated,String workspaceId)throws SwackException{
		
		if(compare(roomName,workspaceId)) {
			// TODO　同名エラーの処理
			return "error"; 
		}
		
		String roomId = getMaxRoomId();
		
		String sql = "INSERT INTO ROOMS2 VALUES(?,?,?,?,?,?)";
		try (Connection conn = DriverManager.getConnection(DB_ENDPOINT, DB_USERID, DB_PASSWORD)) {
			PreparedStatement pStmt = conn.prepareStatement(sql);
			pStmt.setString(1, roomId);
			pStmt.setString(2, roomName);
			pStmt.setString(3, createdUserId);
			pStmt.setBoolean(4, directed);
			pStmt.setBoolean(5, privated);
			pStmt.setString(6, workspaceId);

			pStmt.executeUpdate();
		}catch (SQLException e) {
			throw new SwackException(ERR_DB_PROCESS, e);
		}
		
		insertJoinRoom(roomId,createdUserId);
		if(directed) {
			String[] split = roomName.split(",");
			String pairUserId= split[1];
			insertJoinRoom(roomId,pairUserId);
		}else {
			insertRoomAdmin(roomId,createdUserId);
		}
		
		
		return roomId;
	}
	
	public String getMaxRoomId() throws SwackException {
		String maxRoomId = "";
		
		String sql1 = "SELECT MAX(ROOMID) AS ROOMID FROM ROOMS2";
		try (Connection conn = DriverManager.getConnection(DB_ENDPOINT, DB_USERID, DB_PASSWORD);
			PreparedStatement pStmt = conn.prepareStatement(sql1);
			
			ResultSet rs = pStmt.executeQuery()){ 
			if(rs.next()) {
				maxRoomId = rs.getString("ROOMID"); 
			}
		}catch (SQLException e) {
			throw new SwackException(ERR_DB_PROCESS, e);
		}
		
		// roomIdを+１する
		String[] list = maxRoomId.split("");
		String strId = "";
		strId = list[1] + list[2] + list[3] + list[4];
		int intId = Integer.valueOf(strId);
		intId += 1;
		strId = String.format("%04d", intId);
		maxRoomId = list[0] + strId;
		
		return maxRoomId;
	}
	
	// ワークスペース内で最小のルームIDを返す
	public String getMinRoomId(String workspaceId) throws SwackException {
		String minRoomId = "";
		
		String sql = "SELECT MIN(ROOMID) AS ROOMID FROM ROOMS2 WHERE WORKSPACEID = ?";
		try (Connection conn = DriverManager.getConnection(DB_ENDPOINT, DB_USERID, DB_PASSWORD)){
			PreparedStatement pStmt = conn.prepareStatement(sql);
			pStmt.setString(1, workspaceId);
			ResultSet rs = pStmt.executeQuery();
			if(rs.next()) {
				minRoomId = rs.getString("ROOMID"); 
			}
		}catch (SQLException e) {
			throw new SwackException(ERR_DB_PROCESS, e);
		}
		
		return minRoomId;
	}
	
	// joinroomテーブルにデータ追加 TODO directの処理
	public void insertJoinRoom(String roomId, String userId) throws SwackException {
		
		String sql = "INSERT INTO JOINROOM VALUES(?,?)";
		try (Connection conn = DriverManager.getConnection(DB_ENDPOINT, DB_USERID, DB_PASSWORD)) {
			PreparedStatement pStmt = conn.prepareStatement(sql);
			pStmt.setString(1, roomId);
			pStmt.setString(2, userId);

			pStmt.executeUpdate();
		}catch (SQLException e) {
			throw new SwackException(ERR_DB_PROCESS, e);
		}
		
	}
	
	// roomadminテーブルにデータ追加
	public void insertRoomAdmin(String roomId, String userId) throws SwackException {
		
		String sql = "INSERT INTO ROOMADMIN VALUES(?,?)";
		try (Connection conn = DriverManager.getConnection(DB_ENDPOINT, DB_USERID, DB_PASSWORD)) {
			PreparedStatement pStmt = conn.prepareStatement(sql);
			pStmt.setString(1, roomId);
			pStmt.setString(2, userId);

			pStmt.executeUpdate();
		}catch (SQLException e) {
			throw new SwackException(ERR_DB_PROCESS, e);
		}
		
	}
	
	//workspaceid追加 (これどこで使用してる?)
	public ArrayList<Room> getRoomList(String workspaceId,String userId) throws SwackException {
		// SQL
		String sql = "SELECT ROOMID,ROOMNAME FROM ROOMS2 WHERE WORKSPACEID = ? AND ROOMID IN (SELECT ROOMID FROM JOINROOM WHERE USERID = ?)";

		ArrayList<Room> roomlist = new ArrayList<Room>();

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
				String roomId = rs.getString("ROOMID");
				String roomName = rs.getString("ROOMNAME");
				Room room = new Room(roomId,roomName);

				roomlist.add(room);
			}

		} catch (SQLException e) {
			throw new SwackException(ERR_DB_PROCESS, e);
		}

		return roomlist;

	}
	
	public boolean compare(String roomName,String workspaceId)throws SwackException {
		// roomIdがすでに登録されていた場合の処理
		ArrayList<String> roomNameList = getAllRoomList(workspaceId);
		for(String name : roomNameList) {
			if(name.compareTo(roomName) == 0) {
					return true;
			}
		}
		return false;
	}
	
	public ArrayList<String> getAllRoomList(String workspaceId) throws SwackException {
		// SQL
		String sql = "SELECT ROOMNAME FROM ROOMS2 WHERE WORKSPACEID = ?";

		ArrayList<String> roomNameList = new ArrayList<String>();

		// Access DB
		try (Connection conn = DriverManager.getConnection(DB_ENDPOINT, DB_USERID, DB_PASSWORD)) {

			// SQL作成
			PreparedStatement pStmt = conn.prepareStatement(sql);
			pStmt.setString(1, workspaceId);

			// SQL実行
			ResultSet rs = pStmt.executeQuery();

			// 結果を詰め替え
			while (rs.next()) {
				String roomName = rs.getString("ROOMNAME");
				roomNameList.add(roomName);
			}

		} catch (SQLException e) {
			throw new SwackException(ERR_DB_PROCESS, e);
		}

		return roomNameList;

	}
	
	// ルームの管理者かどうか調べる
	public boolean roomAdminCheck(String userId) throws SwackException {
		boolean admin = false;
		//TODO roomadminテーブルを作成する必要あり
		String sql = "SELECT COUNT(USERID) AS COUNT FROM ROOMADMIN　WHERE USERID = ?";
		
		try (Connection conn = DriverManager.getConnection(DB_ENDPOINT, DB_USERID, DB_PASSWORD)) {

			// SQL作成
			PreparedStatement pStmt = conn.prepareStatement(sql);
			pStmt.setString(1, userId);

			// SQL実行
			ResultSet rs = pStmt.executeQuery();

			// 結果を詰め替え
			int count = 0;
			if (rs.next()) {
				count = rs.getInt("COUNT");
			}
			
			if(count > 0) {
				admin = true;
			}

		} catch (SQLException e) {
			throw new SwackException(ERR_DB_PROCESS, e);
		}
		return admin;
	}
	
	// roomIdに対応した管理者を返す
	public ArrayList<String> getRoomAdminList(String roomId) throws SwackException{
		ArrayList<String> adminList = new ArrayList<String>();
		
		String sql = "SELECT USERID  FROM ROOMADMIN WHERE ROOMID = ?";
		
		try (Connection conn = DriverManager.getConnection(DB_ENDPOINT, DB_USERID, DB_PASSWORD)) {

			// SQL作成
			PreparedStatement pStmt = conn.prepareStatement(sql);
			pStmt.setString(1, roomId);

			// SQL実行
			ResultSet rs = pStmt.executeQuery();

			// 結果を詰め替え
			while (rs.next()) {
				String user = rs.getString("USERID");
				adminList.add(user);
			}
		} catch (SQLException e) {
			throw new SwackException(ERR_DB_PROCESS, e);
		}
		
		return adminList;
	}
}
