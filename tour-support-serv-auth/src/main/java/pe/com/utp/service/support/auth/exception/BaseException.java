package pe.com.utp.service.support.auth.exception;

import java.io.Serializable;

public class BaseException extends Exception implements Serializable {

	private static final long serialVersionUID = 1L;

	private final String code;

	public BaseException(String code, String message, Throwable exception) {
		super(message, exception);
		this.code = code;
	}

	public String getCode() {
		return code;
	}

}
