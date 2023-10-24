package dao;

import static parameter.DAOParameters.*;
import static parameter.Messages.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
	
	public String getMaxRoomId() throws SwackException {
		String maxRoomId = "";
		
		String sql1 = "SELECT MAX(ROOMID) AS ROOMID FROM ROOMS";
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
}
