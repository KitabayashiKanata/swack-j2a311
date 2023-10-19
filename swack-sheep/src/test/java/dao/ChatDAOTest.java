package dao;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import bean.Room;
import exception.SwackException;

class ChatDAOTest {

	@Test
	void testGetRoom() {
		fail("まだ実装されていません");
	}

	@Test
	void testGetRoomList() throws SwackException {
		// 準備
		ChatDAO chatDAO = new ChatDAO();

		// テスト実行
		ArrayList<Room> roomlist
			= chatDAO.getRoomList("U0002");

		// 結果の確認
		System.out.println("testGetRoomList()");
		for(Room room:roomlist) {
			System.out.println(room.toString());
		}

	}

	@Test
	void testGetDirectList() {
		fail("まだ実装されていません");
	}

	@Test
	void testGetChatlogList() {
		fail("まだ実装されていません");
	}

	@Test
	void testSaveChatlog() {
		fail("まだ実装されていません");
	}

}
