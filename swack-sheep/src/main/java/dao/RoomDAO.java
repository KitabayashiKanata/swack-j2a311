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
			return "R0000"; 
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
}
