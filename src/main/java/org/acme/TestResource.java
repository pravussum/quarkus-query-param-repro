package org.acme;

import java.util.Map;

public class TestResource implements MyApi {

	@Override
	public Pojo testEndpoint(Map<String, Object> projection) {
		Pojo pojo = new Pojo();
		pojo.field = projection.get("a").toString();
		return pojo;
	}
}