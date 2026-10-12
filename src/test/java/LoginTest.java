
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.*;
import org.testng.Assert;
import org.testng.annotations.*;
import java.time.Duration;

public class LoginTest {
    WebDriver driver;
    WebDriverWait wait;

    @BeforeMethod
    public void setUp() {
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.get("https://www.saucedemo.com");
    }

    @AfterMethod
    public void tearDown() {
        driver.quit();
    }

   
    public void login() {
    	wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("user-name")))
        .sendKeys("standard_user");
    driver.findElement(By.id("password")).sendKeys("secret_sauce");
    driver.findElement(By.id("login-button")).click();

    }
    
    @Test
    public void validLoginShowsProducts() {
        login();
        String title = wait.until(
            ExpectedConditions.visibilityOfElementLocated(By.className("title"))).getText();
        Assert.assertEquals(title, "Products");
    }
    @Test
    public void addToCartUpdatesBadge() {
    	login();
    	wait.until(ExpectedConditions.elementToBeClickable(By.id("add-to-cart-sauce-labs-backpack"))).click();
    	//String badge = driver.findElement(By.className("shopping_cart_badge")).getText();
    	String badge= wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("shopping_cart_badge"))).getText();
    	Assert.assertEquals(badge,"1");
    	
    	
    }
}