package com.app.request.result;

public final class ClientError extends Result {

	final String errorMessage;

	public ClientError(String errorMessage) {
		super();
		this.errorMessage = errorMessage;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

}
