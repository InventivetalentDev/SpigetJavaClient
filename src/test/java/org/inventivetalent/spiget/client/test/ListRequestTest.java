package org.inventivetalent.spiget.client.test;

import org.inventivetalent.spiget.client.SpigetClient;
import org.junit.Test;
import org.spiget.data.author.Author;
import org.spiget.data.resource.Resource;

import static org.junit.Assert.*;

public class ListRequestTest {

	private SpigetClient client;

	public ListRequestTest() {
		this.client = new SpigetClient("SpigetJavaClient-JUnitTest");
	}

	@Test
	public void resourceListTest() {
		this.client.request("/resources").sorted().getSorted(Resource.class, (response, error) -> {
			if (error != null) { error.printStackTrace(); }
			assertNull(error);

			int first = 0;
			int last = 0;
			int count = 0;
			for (Resource resource : response) {
				count++;
				if (count == 1) { first = resource.getId(); }
				if (count == 10) { last = resource.getId(); }
				System.out.println(resource);
			}

			assertEquals(10, count);// Default Size
			assertTrue(last > first);// Default ascending order
		});
	}

	@Test
	public void authorListTest() {
		this.client.request("/authors").sorted().getSorted(Author.class, (response, error) -> {
			if (error != null) { error.printStackTrace(); }
			assertNull(error);

			int first = 0;
			int last = 0;
			int count = 0;
			for (Author author : response) {
				count++;
				if (count == 1) { first = author.getId(); }
				if (count == 10) { last = author.getId(); }
				System.out.println(author);
			}

			assertEquals(10, count);// Default Size
			assertTrue(last > first);// Default ascending order
		});
	}

}
