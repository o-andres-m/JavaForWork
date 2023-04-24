// Generated by Selenium IDE
import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsNot.not;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Alert;
import org.openqa.selenium.Keys;
import java.util.*;
import java.net.MalformedURLException;
import java.net.URL;
public class TestActoresTest {
  private WebDriver driver;
  private Map<String, Object> vars;
  JavascriptExecutor js;
  @Before
  public void setUp() {
    driver = new ChromeDriver();
    js = (JavascriptExecutor) driver;
    vars = new HashMap<String, Object>();
  }
  @After
  public void tearDown() {
    driver.quit();
  }
  @Test
  public void testActores() {
    driver.get("http://localhost:3000/");
    driver.manage().window().setSize(new Dimension(950, 578));
    assertThat(driver.findElement(By.cssSelector(".btn-group:nth-child(2) > .btn")).getText(), is("Actores"));
    driver.findElement(By.cssSelector(".btn-group:nth-child(2) > .btn")).click();
    assertThat(driver.findElement(By.cssSelector("th:nth-child(1)")).getText(), is("Lista de Actores y Actrices"));
    driver.findElement(By.cssSelector("tr:nth-child(1) .btn-group > .btn:nth-child(1)")).click();
    assertThat(driver.findElement(By.cssSelector("b:nth-child(1)")).getText(), is("Código:"));
    assertThat(driver.findElement(By.cssSelector("b:nth-child(3)")).getText(), is("Nombre:"));
    assertThat(driver.findElement(By.cssSelector("b:nth-child(5)")).getText(), is("Apellidos:"));
    assertThat(driver.findElement(By.cssSelector("p > .btn")).getText(), is("Volver"));
    driver.findElement(By.cssSelector("p > .btn")).click();
  }
}
