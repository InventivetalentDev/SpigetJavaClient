package org.inventivetalent.spiget.client.response;

import java.util.Iterator;

/**
 * Response for sorted data, i.e. Json-Arrays
 *
 * @param <V> Data type to parse (provided by {@link org.spiget.data})
 */
public interface SortedResponse<V> extends Response<V>, Iterable<V> {

	/**
	 * Get the first element in this sorted response
	 *
	 * @return the first data element
	 */
	@Override
	default V data() {
		Iterator<V> iterator = iterator();
		return iterator.hasNext() ? iterator.next() : null;
	}

	/**
	 * Get the page size
	 *
	 * @return page size
	 */
	int size();

	/**
	 * Get the page index
	 *
	 * @return page index
	 */
	int page();

	/**
	 * Get the amount of pages
	 *
	 * @return page amount
	 */
	int count();

}
