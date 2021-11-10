package com.log.event.logger.exception;

public class EventException extends RuntimeException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public EventException(String message) {
		super(message);
	}
	public EventException(String message, Throwable reason) {
		super(message, reason);
	}

}
