package pages;

import lombok.extern.log4j.Log4j2;
import org.testng.Assert;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

@Log4j2
public class LoginPage {

    public LoginPage openPage() {
        log.info("Opening LoginPage...");
        open("/login");

        return this;
    }

    public LoginPage isOpened() {
        try {
            $(byText("Sign in")).shouldBe(visible);
            log.info("LoginPage is opened");
        } catch (Exception e) {
            log.error(e.getMessage());
            Assert.fail("LoginPage isn't opened");
        }

        return this;
    }

    public ProjectPage login(String username, String password) {
        log.info("Log in with credential: '{}', '{}'", username, password);
        $("[name=email]").setValue(username);
        $("[name=password]").setValue(password);
        $(byText("Sign in")).click();

        return new ProjectPage();
    }
}
