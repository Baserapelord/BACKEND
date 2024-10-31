package pe.com.utp.service.user.exception;

public class ServiceException extends BaseException {
	private static final long serialVersionUID = 1L;

	public ServiceException(String string) {
		super(string);
	}

	public ServiceException(Exception exception) {
		super(exception);
	}

	public ServiceException(String string, Exception exception) {
		super(string, exception);
	}
	
	public ServiceException(String code, String message, Exception exception) {
		super(code, message, exception);
	}

	public ServiceException(String code, String message) {
		super(code, message);
	}

}
