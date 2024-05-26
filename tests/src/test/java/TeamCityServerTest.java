import com.applitools.eyes.RectangleSize;
import com.applitools.eyes.selenium.Eyes;
import com.applitools.eyes.selenium.StitchMode;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testcontainers.containers.GenericContainer;

import java.util.concurrent.TimeUnit;

public class TeamCityServerTest {

    private WebDriver driver;
    private Eyes eyes;
    private GenericContainer teamCityContainer;

    @BeforeClass
    public void setUp() {
        String currentDirectory = System.getProperty("user.dir");
        System.out.println("Current working directory: " + currentDirectory);
        // Start TeamCity server in a Docker container
//        teamCityContainer = new GenericContainer(DockerImageName.parse("jetbrains/teamcity-server"))
//                .withExposedPorts(8111);
//        teamCityContainer.withFileSystemBind(currentDirectory + "/docker", "/data/teamcity_server/datadir");
//        teamCityContainer.start();

        // Set up WebDriver
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        driver = new ChromeDriver(options);

        // Set up Applitools Eyes
        eyes = new Eyes();
        eyes.setStitchMode(StitchMode.SCROLL);
    }

    @Test
    public void testTeamCityServer() {
        String serverUrl = "http://localhost:8111";
        driver.get(serverUrl);
        eyes.open(driver, "TeamCity Server", "TeamCity Eyes Plugin", new RectangleSize(1200,800));

        // Wait for the login page to load for up to 500 seconds
        long timeout = 120; // 120 seconds
        long startTime = System.currentTimeMillis();
        while (driver.findElements(By.id("loginPage")).isEmpty()) {
            int timeElapsed = (int) (System.currentTimeMillis() - startTime) / 1000;
            if (timeElapsed >= timeout) {
                throw new RuntimeException("Timed out waiting for login page to load");
            }

            String title = driver.getTitle();
            System.out.println("Time elapsed: " + timeElapsed + "s ; Page title: '" + title + "'");
            sleep(5000);
        }
        eyes.checkWindow("login");

        // Find the username input field and enter 'applitools'
        driver.findElement(By.id("username")).sendKeys("applitools");

        // Find the password input field and enter the password from the environment variable
        String password = System.getenv("TEAMCITY_PASSWORD");
        driver.findElement(By.id("password")).sendKeys(password);

        // Find the login button and click it
        driver.findElement(By.name("submitLogin")).click();
        waitForPage("Favorite Projects — TeamCity", timeout);
        eyes.checkWindow(driver.getTitle());
        driver.get("http://localhost:8111/buildConfiguration/IntegrationsTest_Build");
        waitForPage("Build — TeamCity", timeout);
        eyes.checkWindow(driver.getTitle());
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);


        //////////////
        driver.findElement(By.cssSelector("button.OverviewSystemProblems__showDetails--tl")).click();
        eyes.checkWindow(driver.getTitle());
        /////////////

        // Find the 'Run' button and click it
        driver.findElement(By.cssSelector("button[data-test='run-build']")).click();
        eyes.checkWindow(driver.getTitle());
        driver.findElements(By.cssSelector(".BuildDetails__container--qM button.Details__button--h4")).get(0).click();
        waitForPage("Build / #", timeout);
        eyes.close();
    }

    private void waitForPage(String prefixTitle, long timeout) {
        long startTime = System.currentTimeMillis();
        String pageTitle = driver.getTitle();
        while (pageTitle == null || !pageTitle.startsWith(prefixTitle)) {
            int timeElapsed = (int) (System.currentTimeMillis() - startTime) / 1000;
            if (timeElapsed >= timeout) {
                throw new RuntimeException("Timed out waiting for '" + prefixTitle + "' page to load");
            }
            sleep(250);
            pageTitle = driver.getTitle();
        }
    }

    private static void sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @AfterClass
    public void tearDown() {
        driver.quit();
        eyes.abortIfNotClosed();
//        teamCityContainer.stop();
    }
}