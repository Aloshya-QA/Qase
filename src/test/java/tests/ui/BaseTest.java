package tests.ui;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.testng.annotations.*;
import pages.LoginPage;
import pages.ProjectPage;
import steps.LoginStep;
import utils.PropertyReader;
import utils.TestListener;

import java.util.Collections;
import java.util.HashMap;

import static com.codeborne.selenide.WebDriverRunner.getWebDriver;

@Log4j2
@Listeners(TestListener.class)
public class BaseTest {

    LoginPage loginPage;
    ProjectPage projectPage;
    LoginStep loginStep;

    String user = System.getProperty("user", PropertyReader.getProperty("user"));
    String password = System.getProperty("password", PropertyReader.getProperty("password"));

    @Parameters({"browser"})
    @BeforeMethod(alwaysRun = true)
    public void setup(@Optional("chrome") String browser) {
        SelenideLogger.addListener("AllureSelenide",new AllureSelenide());

        if (browser.equalsIgnoreCase("chrome")) {
            log.info("Starting Chrome browser");
            Configuration.browser = "chrome";
            Configuration.browserCapabilities = getChromeOptions();
        } else if (browser.equalsIgnoreCase("edge")) {
            log.info("Starting Edge browser");
            Configuration.browser = "edge";
            Configuration.browserCapabilities = getFirefoxOptions();
        }

        Configuration.baseUrl = "https://app.qase.io";
        Configuration.timeout = 10000;
        Configuration.clickViaJs = true;
        Configuration.browserSize = null;

        loginPage = new LoginPage();
        projectPage = new ProjectPage();
        loginStep = new LoginStep();
    }

    private static ChromeOptions getChromeOptions() {
        log.info("Init chrome options");
        ChromeOptions options = new ChromeOptions();
        HashMap<String, Object> chromePrefs = new HashMap<>();
        chromePrefs.put("credentials_enable_service", false);
        chromePrefs.put("profile.password_manager_enabled", false);
        options.setExperimentalOption("prefs", chromePrefs);
        options.addArguments("--incognito");
        options.addArguments("--disable-notifications");
        options.addArguments("--disable-popup-blocking");
        options.addArguments("--disable-infobars");
//        options.addArguments("--headless");
//        options.addArguments("--no-sandbox");
//        options.addArguments("--disable-dev-shm-usage");
//        options.addArguments("--disable-gpu");
        options.addArguments("--start-maximized");
        options.addArguments("--disable-blink-features=AutomationControlled");
        options.setExperimentalOption("excludeSwitches",
                Collections.singletonList("enable-automation"));
        return options;
    }

    private static FirefoxOptions getFirefoxOptions() {
        FirefoxOptions options = new FirefoxOptions();
        options.addArguments("--start-maximized");
        return options;
    }

    @AfterMethod(alwaysRun = true)
    public void TearDawn() {
        if ( getWebDriver() != null) {
            log.info("Closing browser");
            getWebDriver().quit();
        }
    }
}
