package org.inventivetalent.spiget.client.request;

import com.google.gson.JsonElement;
import org.inventivetalent.spiget.client.Callback;
import org.inventivetalent.spiget.client.response.Response;

public interface Request {

	/**
	 * Get the raw json data
	 *
	 * @param callback {@link Callback}
	 */
	void getJson(Callback<JsonElement> callback);

	/**
	 * Get and parse the data
	 *
	 * @param type     Class to parse
	 * @param callback {@link Callback}
	 * @param <T>      Data type
	 */
	<T> void get(Class<T> type, Callback<Response<T>> callback);

	/**
	 * Make this request sorted
	 *
	 * @return {@link SortedRequest}
	 */
	SortedRequest sorted();

	/**
	 * Add a field to return
	 *
	 * @param field field
	 * @return this request
	 */
	Request field(String field);

	/**
	 * Add fields to return
	 *
	 * @param fields fields
	 * @return this request
	 */
	Request fields(String... fields);

	/**
	 * Add fields to return
	 *
	 * @param fields fields
	 * @return this request
	 */
	Request fields(Iterable<String> fields);

}
