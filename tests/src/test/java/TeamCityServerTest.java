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
import org.testcontainers.utility.DockerImageName;

public class TeamCityServerTest {

    private WebDriver driver;
    private Eyes eyes;
    private GenericContainer teamCityContainer;

    @BeforeClass
    public void setUp() {
        String currentDirectory = System.getProperty("user.dir");
        System.out.println("Current working directory: " + currentDirectory);
        // Start TeamCity server in a Docker container
        teamCityContainer = new GenericContainer(DockerImageName.parse("jetbrains/teamcity-server"))
                .withExposedPorts(8111);
        teamCityContainer.withFileSystemBind("", "/data/teamcity_server/datadir");
        teamCityContainer.start();

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
        String serverUrl = "http://localhost:" + teamCityContainer.getMappedPort(8111);
        driver.get(serverUrl);

        eyes.open(driver, "TeamCity Server", "Initial View");
        eyes.checkWindow();
        eyes.close();
    }

    @AfterClass
    public void tearDown() {
        driver.quit();
        eyes.abortIfNotClosed();
        teamCityContainer.stop();
    }
}