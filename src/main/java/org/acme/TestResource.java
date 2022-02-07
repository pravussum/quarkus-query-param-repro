package org.acme;

import java.util.Map;

public class TestResource implements MyApi {

	@Override
	public Pojo testEndpoint(Map<String, Object> projection) {
		Pojo pojo = new Pojo();
		pojo.field = "field";
		return pojo;
	}
}