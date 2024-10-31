package pe.com.utp.service.user.exception;

import java.io.Serializable;

public class BaseException extends Exception implements Serializable {

	private static final long serialVersionUID = 1L;

	private Exception exception;
	private String code;
	private String message;
	private String messageException;

	public BaseException(String code, String message, Exception exception) {
		super(message, exception);
		this.exception = exception;
		this.code = code;
		this.message = message;
	}

	public BaseException(String code, String message) {
		super(message);
		this.code = code;
		this.message = message;
	}

	public BaseException(String message, Exception exception) {
		super(message, exception);
		this.exception = exception;
		this.message = message;
	}

	public BaseException(Exception exception) {
		super(exception);
		this.exception = exception;
	}

	public BaseException(String message) {
		super(message);
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public Exception getException() {
		return exception;
	}

	public void setException(Exception exception) {
		this.exception = exception;
	}

	public String getCode() {
		return code;
	}

	public String getMessageException() {
		return messageException;
	}

}
