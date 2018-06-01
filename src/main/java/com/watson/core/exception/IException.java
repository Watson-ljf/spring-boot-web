package com.watson.core.exception;

/**
 * IException
 */
public abstract class IException extends Exception {
	private static final long serialVersionUID = -1582874427218948396L;
	private int code;

	public IException(int code, String message) {
		super(message);
		this.code = code;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}
}
