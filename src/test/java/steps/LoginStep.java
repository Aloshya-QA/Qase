package steps;

import pages.LoginPage;

public class LoginStep {

    LoginPage loginPage;

    public LoginStep() {
        loginPage = new LoginPage();
    }

    public void auth(String user, String password) {
        loginPage.openPage()
                .isOpened()
                .login(user, password)
                .isOpened();
    }
}
