package exception;

/**
 * アプリケーション独自のException定義クラス
 */
public class SwackException extends Exception {
	private static final long serialVersionUID = 1L;

	public SwackException() {
		super();
	}

	public SwackException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public SwackException(String message, Throwable cause) {
		super(message, cause);
	}

	public SwackException(String message) {
		super(message);
	}

	public SwackException(Throwable cause) {
		super(cause);
	}

}
