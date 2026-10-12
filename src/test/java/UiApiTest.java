
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.*;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import io.restassured.path.json.JsonPath;

import java.time.Duration;
import java.util.*;
import static io.restassured.RestAssured.*;

public class UiApiTest {

	WebDriver driver;
	
	@BeforeMethod
	public void setup() {
		driver= new ChromeDriver();
	}
	
	@AfterMethod
	public void tearDown() {
		driver.quit();
	}
	
	
    @Test
    public void uiTitlesMatchApiTitles() {

        // ---- API side ----
        
    	JsonPath json =
        	    given().baseUri("https://demoqa.com")
        	    .when().get("/BookStore/v1/Books")
        	    .then().statusCode(200)
        	    .extract().jsonPath();

        	List<String> apiTitles = json.getList("books.title");
        	List<String> apiAuthors = json.getList("books.author");

        	List<String> apiPairs = new ArrayList<>();
        	for (int i = 0; i < apiTitles.size(); i++) {
        	    apiPairs.add(apiTitles.get(i) + " | " + apiAuthors.get(i));
        	}
        	
        // ---- UI side ----
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.get("https://demoqa.com/books");
        
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(
        	    By.cssSelector(".action-buttons a")));
        
        	List<WebElement> dropdowns = driver.findElements(By.cssSelector("select"));
        	if (!dropdowns.isEmpty()) {
        	    new Select(dropdowns.get(0)).selectByValue("100");
        	}

        	wait.until(d -> d.findElements(By.cssSelector(".action-buttons a")).size()
        	                == apiTitles.size());

        	//List<WebElement> cells = driver.findElements(By.cssSelector(".action-buttons a"));

        	List<String> uiPairs = new ArrayList<>();
        	for (WebElement row : driver.findElements(By.cssSelector("tbody tr"))) {
        	    List<WebElement> tds = row.findElements(By.tagName("td"));
        	    if (tds.size() >= 3) {
        	        uiPairs.add(tds.get(1).getText().trim() + " | " + tds.get(2).getText().trim());
        	    }
        	}

        	Assert.assertFalse(apiPairs.isEmpty(), "API returned no books");
        	Assert.assertEquals(uiPairs, apiPairs, "UI and API title/author pairs differ");


        
    }
}