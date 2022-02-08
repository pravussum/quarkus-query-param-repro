package org.acme;

import java.util.Map;

import io.smallrye.mutiny.Uni;

public class TestResource implements MyApi {

	@Override
	public Uni<Pojo> testEndpoint(Map<String, Object> projection) {
		Pojo pojo = new Pojo();
		pojo.field = projection.get("a").toString();
		return Uni.createFrom().item(pojo);
	}
}