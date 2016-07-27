package org.inventivetalent.spiget.client.test;

import org.inventivetalent.spiget.client.SpigetClient;
import org.junit.Test;
import org.spiget.data.resource.Resource;

import static org.junit.Assert.*;

public class FieldRequestTest {

	private SpigetClient client;

	public FieldRequestTest() {
		this.client = new SpigetClient("SpigetJavaClient-JUnitTest");
	}

	@Test
	public void resourceFieldTest() {
		this.client.request("/resources/5583").field("id").field("name").get(Resource.class,(response,error)->{
			Resource resource = response.data();

			// Requested fields
			assertEquals(5583, resource.getId());
			assertNotNull(resource.getName());
			assertTrue(resource.getName().contains("AnimatedFrames"));

			// Ignored fields
			assertNull(resource.getTag());
			assertNull(resource.getDescription());
			assertNull(resource.getCategory());
			assertNull(resource.getAuthor());
			assertNull(resource.getVersion());
		});
	}

}
