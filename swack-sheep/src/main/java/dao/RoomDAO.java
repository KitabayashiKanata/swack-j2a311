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
		for (int i = 1; i < list.length;i++){
			strId.concat(list[i]);
		}
		int intId = Integer.valueOf(strId);
		intId += 1;
		maxRoomId = list[0] + String.valueOf(intId);
		
		
		return maxRoomId;
	}
}
