package org.inventivetalent.spiget.client;

import org.inventivetalent.spiget.client.response.Response;

class DefaultResponse<V> implements Response<V> {

	private final Class<V> type;
	private final V        data;

	DefaultResponse(Class<V> type, V data) {
		this.type = type;
		this.data = data;
	}

	DefaultResponse(DefaultResponse<V> other) {
		this.type = other.type;
		this.data = other.data;
	}

	@Override
	public V data() {
		return this.data;
	}


}
