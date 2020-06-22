package com.scout.exception;

import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * This is used to handle the exception in case address not found
 * @author Kushagra Mathur
 *
 */
@ResponseStatus
public class AddressNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	/**
	 * @param message {@link String}
	 */
	public AddressNotFoundException(String message) {
		super(message);
	}

	/**
	 * @param message {@link String}
	 * @param throwable {@link Throwable}
	 */
	public AddressNotFoundException(String message, Throwable throwable) {
		super(message, throwable);
	}
}
