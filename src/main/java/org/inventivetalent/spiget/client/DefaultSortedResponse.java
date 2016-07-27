package org.inventivetalent.spiget.client;

import com.google.gson.JsonArray;
import org.inventivetalent.spiget.client.response.SortedResponse;

import java.util.Iterator;

class DefaultSortedResponse<V> implements SortedResponse<V> {

	private final Class<V>  type;
	private final JsonArray jsonArray;
	private final int       size;
	private final int       page;
	private final int       count;

	DefaultSortedResponse(Class<V> type, JsonArray jsonArray, int size, int page, int count) {
		this.type = type;
		this.jsonArray = jsonArray;
		this.size = size;
		this.page = page;
		this.count = count;
	}

	@Override
	public int size() {
		return this.size;
	}

	@Override
	public int page() {
		return this.page;
	}

	@Override
	public int count() {
		return this.count;
	}

	@Override
	public Iterator<V> iterator() {
		return new SortedResponseIterator();
	}

	class SortedResponseIterator implements Iterator<V> {

		int index = 0;

		@Override
		public boolean hasNext() {
			return this.index < size();
		}

		@Override
		public V next() {
			V v = SpigetClient.GSON.fromJson(jsonArray.get(index).getAsJsonObject(), type);
			index++;
			return v;
		}
	}
}
