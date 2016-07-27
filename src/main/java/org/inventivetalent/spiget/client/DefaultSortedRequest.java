package org.inventivetalent.spiget.client;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.inventivetalent.spiget.client.request.SortedRequest;
import org.inventivetalent.spiget.client.response.SortedResponse;

class DefaultSortedRequest extends DefaultRequest implements SortedRequest {

	private static final String SORT_FORMAT = "?size=%s&page=%s&sort=%s";

	private int    size = 10;
	private int    page = 1;
	private String sort = "id";

	DefaultSortedRequest(SpigetClient client, String path) {
		super(client, path);
	}

	DefaultSortedRequest(DefaultRequest other) {
		super(other);
	}

	@Override
	public <T> void getSorted(Class<T> type, Callback<SortedResponse<T>> callback) {
		this.client.getDirect(this.path + String.format(SORT_FORMAT, this.size, this.page, this.sort) + buildFields("&"), (response, error) -> {
			if (error != null) {
				callback.call(null, error);
				return;
			}
			JsonElement jsonElement = SpigetClient.JSON_PARSER.parse(response.body());
			if (!jsonElement.isJsonArray()) {
				if (jsonElement.isJsonObject()) {// Handle error
					JsonObject jsonObject = jsonElement.getAsJsonObject();
					if (jsonObject.has("error")) {
						callback.call(null, new SpigetError(response.statusCode(), jsonObject.get("error").getAsString()));
						return;
					}
				}

				callback.call(null, new IllegalArgumentException("response is not a json array"));
				return;
			}
			int pageSize = Integer.parseInt(response.header("X-Page-Size"));
			int pageIndex = Integer.parseInt(response.header("X-Page-Index"));
			int pageCount = Integer.parseInt(response.header("X-Page-Count"));
			callback.call(new DefaultSortedResponse<>(type, jsonElement.getAsJsonArray(), pageSize, pageIndex, pageCount), null);
		});
	}

	@Override
	public SortedRequest size(int size) {
		this.size = size;
		return this;
	}

	@Override
	public SortedRequest page(int page) {
		this.page = page;
		return this;
	}

	@Override
	public SortedRequest sort(String sort, SortOrder order) {
		this.sort = order.format(sort);
		return this;
	}

	@Override
	public SortedRequest sort(String sort) {
		this.sort = sort;
		return this;
	}

}
