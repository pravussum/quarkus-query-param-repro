package org.acme;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
public class TestResourceTest {

    @Test
    public void testEndpoint() {
        given()
          .when()
		        .queryParam("testqueryparam", "{\"a\":\"b\",\"c\":\"d\"}")
		        .get("/endpoint")

          .then()
             .statusCode(200)
             .body(is("{\"field\":\"b\"}"));
    }

}