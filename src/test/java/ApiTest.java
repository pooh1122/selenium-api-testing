import org.testng.annotations.Test;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class ApiTest {


	@Test
	public void getUserReturnsCorrectName() {
		
		given().log().all()
		.baseUri("https://jsonplaceholder.typicode.com")
		.when()
		.get("/users/1")
		.then()
		.log().body()
		.statusCode(200)
		.body("name", equalTo("Leanne Graham"));
	}
	
	@Test
	public void createPostReturns201() {
		given()
	    .baseUri("https://jsonplaceholder.typicode.com")
	    .header("Content-Type", "application/json")
	    .body("{ \"title\": \"my first post\", \"body\": \"hello\", \"userId\": 1 }")
	.when()
	    .post("/posts")
	.then()
	    .log().body()
	    .statusCode(201)
	    .body("title", equalTo("my first post"));
	}
}
