package org.acme;

import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@RegisterRestClient
@Path("")
public interface MyApi {

	@GET
	@Path("/endpoint")
	@Produces({ "application/json" })
	Pojo testEndpoint(@QueryParam("testqueryparam") Map<String, Object> projection);

}
