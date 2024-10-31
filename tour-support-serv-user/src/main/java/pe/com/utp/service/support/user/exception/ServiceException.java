package pe.com.utp.service.support.user.exception;

public class ServiceException extends BaseException {
	private static final long serialVersionUID = 1L;

	public ServiceException(String code, String message, Exception exception) {
		super(code, message, exception);
	}

}
