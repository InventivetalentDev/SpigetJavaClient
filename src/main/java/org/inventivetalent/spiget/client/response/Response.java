package org.inventivetalent.spiget.client.response;

/**
 * Default response for Json-Objects
 *
 * @param <V> Data type to parse (provided by {@link org.spiget.data})
 */
public interface Response<V> {

	/**
	 * Get the data of this response
	 *
	 * @return the data
	 */
	V data();

}
