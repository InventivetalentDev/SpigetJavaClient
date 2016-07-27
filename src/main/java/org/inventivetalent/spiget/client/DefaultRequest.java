package org.inventivetalent.spiget.client;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.inventivetalent.spiget.client.request.Request;
import org.inventivetalent.spiget.client.request.SortedRequest;
import org.inventivetalent.spiget.client.response.Response;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

class DefaultRequest implements Request {

	protected final SpigetClient client;
	protected final String       path;
	protected final Set<String> fields = new HashSet<>();

	DefaultRequest(SpigetClient client, String path) {
		this.client = client;
		this.path = path;
	}

	DefaultRequest(DefaultRequest other) {
		this.client = other.client;
		this.path = other.path;
	}

	@Override
	public SortedRequest sorted() {
		return new DefaultSortedRequest(this);
	}

	@Override
	public Request field(String field) {
		this.fields.add(field);
		return this;
	}

	@Override
	public Request fields(String... fields) {
		this.fields.addAll(Arrays.asList(fields));
		return this;
	}

	@Override
	public Request fields(Iterable<String> fields) {
		for (String field : fields)
			this.fields.add(field);
		return this;
	}

	protected String buildFields(String startChar) {
		if (this.fields.isEmpty()) {
			return "";
		}
		StringBuilder builder = new StringBuilder(startChar);
		builder.append("fields=");
		boolean first = true;
		for (String field : this.fields) {
			if (!first) { builder.append(','); }
			builder.append(field);
			first = false;
		}
		return builder.toString();
	}

	@Override
	public void getJson(Callback<JsonElement> callback) {
		client.getJson(this.path, callback);
	}

	@Override
	public <T> void get(Class<T> type, Callback<Response<T>> callback) {
		this.client.getDirect(this.path + buildFields("?"), (response, error) -> {
			if (error != null) {
				callback.call(null, error);
				return;
			}
			JsonElement jsonElement = SpigetClient.JSON_PARSER.parse(response.body());
			if (jsonElement.isJsonArray()) {
				callback.call(null, new IllegalArgumentException("response is an array"));
			} else if (!jsonElement.isJsonObject()) {
				callback.call(null, new IllegalArgumentException("response is not a json object"));
			} else {
				JsonObject jsonObject = jsonElement.getAsJsonObject();
				if (jsonObject.has("error")) {
					callback.call(null, new SpigetError(response.statusCode(), jsonObject.get("error").getAsString()));
					return;
				}

				callback.call(new DefaultResponse<>(type, SpigetClient.GSON.fromJson(jsonObject, type)), null);
			}
		});
	}

}
