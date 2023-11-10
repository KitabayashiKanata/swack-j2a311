package parameter;

/**
 * データベースアクセス情報管理クラス
 */
public class DAOParameters {

	/** DBエンドポイント(スキーマ名付き) */
	public static final String DB_ENDPOINT = "jdbc:postgresql://192.168.56.101:5501/appdb"; // JUnitから呼ぶ場合
//	public static final String DB_ENDPOINT = "jdbc:postgresql://192.168.56.102:5432/appdb"; 
	/** DBユーザ */
	public static final String DB_USERID = "postgres";
	/** DBパスワード */
	public static final String DB_PASSWORD = "postgres";

}
 