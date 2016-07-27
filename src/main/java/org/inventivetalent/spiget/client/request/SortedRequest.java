package org.inventivetalent.spiget.client.request;

import org.inventivetalent.spiget.client.Callback;
import org.inventivetalent.spiget.client.response.SortedResponse;

public interface SortedRequest {

	/**
	 * Get and parse the sorted data
	 *
	 * @param type     Class to parse
	 * @param callback {@link Callback}
	 * @param <T>      Data type
	 */
	<T> void getSorted(Class<T> type, Callback<SortedResponse<T>> callback);

	/**
	 * Set the page size
	 *
	 * @param size page size
	 * @return this request
	 */
	SortedRequest size(int size);

	/**
	 * Set the page index
	 *
	 * @param page page index
	 * @return this request
	 */
	SortedRequest page(int page);

	/**
	 * Set the field to sort
	 *
	 * @param sort  sort field
	 * @param order sort order
	 * @return this request
	 */
	SortedRequest sort(String sort, SortOrder order);

	/**
	 * Set the field to sort
	 *
	 * @param sort sort field
	 * @return this request
	 * @see #sort(String, SortOrder)
	 */
	SortedRequest sort(String sort);

	enum SortOrder {
		ASCENDING(1, "+"),
		DESCENDING(-1, "-");

		public final int    order;
		public final String prefix;

		SortOrder(int order, String prefix) {
			this.order = order;
			this.prefix = prefix;
		}

		public String format(String field) {
			return this.prefix + field;
		}

	}

}
