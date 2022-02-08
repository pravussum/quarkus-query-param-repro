package org.acme;

import java.util.Map;

import io.smallrye.mutiny.Uni;
import javax.ws.rs.QueryParam;

public class TestResource implements MyApi {

	@Override
	public Uni<Pojo> testEndpoint(@QueryParam("testqueryparam") Map<String, Object> projection) {
		Pojo pojo = new Pojo();
		pojo.field = projection.get("a").toString();
		return Uni.createFrom().item(pojo);
	}
}