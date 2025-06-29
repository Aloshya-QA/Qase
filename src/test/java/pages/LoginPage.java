package pages;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class LoginPage {

    public LoginPage openPage() {
        open("/login");
        return this;
    }

    public LoginPage isOpened() {
        $(byText("Sign in")).shouldBe(visible);
        return this;
    }

    public ProjectPage login(String username, String password) {
        $("[name=email]").setValue(username);
        $("[name=password]").setValue(password);
        $(byText("Sign in")).click();
        return new ProjectPage();
    }
}
