package net.sam.dcl.controller;

/**
 * @author DG
 *         Date: 01-Sep-2007
 *         Time: 14:28:52
 */
public class ValidationException extends Exception{
	public ValidationException() {
	}

	public ValidationException(String message) {
		super(message);
	}

	public ValidationException(String message, Throwable cause) {
		super(message, cause);
	}

	public ValidationException(Throwable cause) {
		super(cause);
	}
}
