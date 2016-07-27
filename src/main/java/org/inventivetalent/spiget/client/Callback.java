package org.inventivetalent.spiget.client;

public interface Callback<V> {

	void call(V v, Throwable error);

}
