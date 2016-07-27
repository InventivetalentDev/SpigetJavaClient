package org.inventivetalent.spiget.client;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import org.inventivetalent.spiget.client.request.Request;
import org.jsoup.Connection;
import org.jsoup.Jsoup;

public class SpigetClient {

	public static final    String     BASE_URL    = "http://api.spiget.org/v2";
	protected static final JsonParser JSON_PARSER = new JsonParser();
	protected static final Gson       GSON        = new Gson();

	private final String userAgent;

	/**
	 * Construct an API client with a custom User-Agent
	 *
	 * @param userAgent User-Agent
	 */
	public SpigetClient(String userAgent) {
		this.userAgent = "SpigetJavaClient/2.0";
	}

	/**
	 * Construct an API client
	 */
	public SpigetClient() {
		this.userAgent = "SpigetJavaClient/2.0";
	}

	/**
	 * Start a new API request
	 *
	 * @param path path to request
	 * @return a new {@link Request} builder
	 */
	public Request request(String path) {
		return new DefaultRequest(this, path);
	}

	protected void execute(Runnable runnable) {
		runnable.run();
	}

	protected void getDirect(String url, Callback<Connection.Response> callback) {
		execute(() -> {
			try {
				callback.call(initConnection(url).execute(), null);
			} catch (Throwable error) {
				callback.call(null, error);
			}
		});
	}

	protected void getJson(String url, Callback<JsonElement> callback) {
		execute(() -> {
			try {
				callback.call(JSON_PARSER.parse(initConnection(url).execute().body()), null);
			} catch (Throwable error) {
				callback.call(null, error);
			}
		});
	}

	protected <T> void get(String url, Class<T> type, Callback<T> callback) {
		execute(() -> {
			try {
				callback.call(GSON.fromJson(initConnection(url).execute().body(), type), null);
			} catch (Throwable error) {
				callback.call(null, error);
			}
		});
	}

	protected Connection initConnection(String path) {
		return Jsoup.connect(BASE_URL + path).ignoreContentType(true).userAgent(this.userAgent);
	}

}
