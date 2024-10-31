package pe.com.utp.service.user.exception;

public class DBException extends BaseException {
	private static final long serialVersionUID = 1L;

	public DBException(String string) {
		super(string);
	}

	public DBException(Exception exception) {
		super(exception);
	}

	public DBException(String string, Exception exception) {
		super(string, exception);
	}
	
	public DBException(String code, String message, Exception exception) {
		super(code, message, exception);
	}

	public DBException(String code, String message) {
		super(code, message);
	}
	
}
