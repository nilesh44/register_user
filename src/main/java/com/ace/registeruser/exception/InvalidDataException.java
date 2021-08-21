package com.ace.registeruser.exception;

public class InvalidDataException  extends RuntimeException {

	private static final long serialVersionUID = -3603166237297582052L;

	public InvalidDataException() {
		super();
	}

	public InvalidDataException(String arg0, Throwable arg1, boolean arg2, boolean arg3) {
		super(arg0, arg1, arg2, arg3);
	}

	public InvalidDataException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public InvalidDataException(String arg0) {
		super(arg0);
	}

	public InvalidDataException(Throwable arg0) {
		super(arg0);
	}

}
