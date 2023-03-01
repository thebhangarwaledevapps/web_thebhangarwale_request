package com.app.request.result;

public final class Success<T> extends Result<T> {

	private final T data;

	public Success(T data) {
		super();
		this.data = data;
	}

	public T getData() {
		return data;
	}
	
}

