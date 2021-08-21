package com.ace.registeruser.exception;

public class RecordAlradyPresent extends RuntimeException {


	private static final long serialVersionUID = 725060797388307486L;

	public RecordAlradyPresent() {
		super();
	}

	public RecordAlradyPresent(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public RecordAlradyPresent(String message, Throwable cause) {
		super(message, cause);
	}

	public RecordAlradyPresent(String message) {
		super(message);
	}

	public RecordAlradyPresent(Throwable cause) {
		super(cause);
	}
	
	

}
