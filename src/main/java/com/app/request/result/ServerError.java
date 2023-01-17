package com.app.request.result;

public final class ServerError extends Result {

	private final Exception exception;

	public ServerError(Exception exception) {
		super();
		this.exception = exception;
	}

	public Exception getException() {
		return exception;
	}
}




