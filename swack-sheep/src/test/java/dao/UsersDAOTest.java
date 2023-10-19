package dao;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import bean.User;
import exception.SwackException;

class UsersDAOTest {

	@Test
	void testSelect() throws SwackException {
		UsersDAO usersDAO = new UsersDAO();

		User userT
		= usersDAO.select("taro@swack.com", "swack0001");

		System.out.println("testSelectSuccess():" + userT.toString());
		assertNotNull(userT);
	}

}
