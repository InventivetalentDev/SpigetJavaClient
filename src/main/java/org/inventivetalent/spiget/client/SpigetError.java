package org.inventivetalent.spiget.client;

/**
 * Error returned by the Spiget API
 */
public class SpigetError extends RuntimeException {

	private final int code;

	public SpigetError(int code, String message) {
		super(message);
		this.code = code;
	}

	/**
	 * Get the response code
	 *
	 * @return HTTP response code
	 */
	public int getCode() {
		return code;
	}

	/**
	 * Get the error message
	 *
	 * @return error message
	 */
	@Override
	public String getMessage() {
		return super.getMessage();
	}
}
