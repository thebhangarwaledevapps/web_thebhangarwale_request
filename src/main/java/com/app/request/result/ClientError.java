package com.app.request.result;

public final class ClientError extends Result {

	private final Exception exception;

	public ClientError(Exception exception) {
		super();
		this.exception = exception;
	}

	public Exception getException() {
		return exception;
	}
}
