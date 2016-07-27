package org.inventivetalent.spiget.client.test;

import org.inventivetalent.spiget.client.SpigetClient;
import org.junit.Test;
import org.spiget.data.resource.Resource;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;

public class SortRequestTest {

	private SpigetClient client;

	public SortRequestTest() {
		this.client = new SpigetClient("SpigetJavaClient-JUnitTest");
	}

	@Test
	public void resourceListSizeTest() {
		this.client.request("/resources").sorted().size(100).getSorted(Resource.class, (response, error) -> {
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

			assertEquals(100, count);
			assertTrue(last > first);// Default ascending order
		});
	}

	@Test
	public void resourceListSortNameTest() {
		this.client.request("/resources").sorted().sort("name").getSorted(Resource.class, (response, error) -> {
			if (error != null) { error.printStackTrace(); }
			assertNull(error);

			List<String> resourceList = new ArrayList<>();
			List<String> sortedResourceList = new ArrayList<>();
			for (Resource resource : response) {
				resourceList.add(resource.getName());
				sortedResourceList.add(resource.getName());
				System.out.println(resource);
			}

			Collections.sort(sortedResourceList);

			assertEquals(sortedResourceList, resourceList);
		});
	}

}
