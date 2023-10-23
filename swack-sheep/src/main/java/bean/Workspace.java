package bean;

import java.io.Serializable;

/**
 * ワークスペース情報を管理するBean
 */
public class Workspace implements Serializable{
	private static final long serialVersionUID = 1L;
	
	/** ユーザ名 */
	private String userName;
	/** メールアドレス */
	private String mailAddress;
	/** パスワード */
	private String password;
	
	public Workspace() {
	}
	
	public Workspace(String userName, String mailAddress, String password) {
		this.userName = userName;
		this.mailAddress = mailAddress;
		this.password = password;
	}
	
	public String getUserName() {
		return userName;
	}
	public String getMailAddress() {
		return mailAddress;
	}
	public String getPassword() {
		return password;
	}
}
