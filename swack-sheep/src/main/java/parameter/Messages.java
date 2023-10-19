
package parameter;

/**
 * システムメッセージ定義クラス
 */
public class Messages {

	/** システム全体 */
	public static final String ERR_SYSTEM = "システムエラーが発生しました";

	/** DB */
	public static final String ERR_DB_CONNECT = "データベースへの接続時にエラーが発生しました";
	public static final String ERR_DB_PROCESS = "データベースの処理中にエラーが発生しました";
	public static final String ERR_DB_CLOSE = "データベースからの切断時にエラーが発生しました";

	/** ログイン */
	public static final String ERR_SESSION_TIMEOUT = "ログイン情報の取得に失敗しました。再度ログインしてください。";
	public static final String ERR_LOGIN_PARAM_MISTAKE = "メールアドレス、またはパスワードに誤りがあります。入力項目を確認し、再度ログインしてください。";
	public static final String INFO_BIGIN = "ワークスペースに参加済みの方はログインしてください。初めての方はワークスペースに参加してください。";

	/** ユーザ管理 */
	public static final String ERR_USERS_ISREGISTERED = "このユーザは登録済みです。入力項目を確認し、登録し直してください。";
	public static final String ERR_USERS_PARAM_MISTAKE = "ユーザ情報に誤りがあります。入力項目を確認し、再度登録してください。";
	public static final String INFO_USERS_ENTRY_SUCCESS = "ワークスペースに参加しました。ログインしてください。";

}