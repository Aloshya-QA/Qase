package tests;

import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;


public class ProjectTest extends BaseTest{

    @Test(testName = "Проверка удаления проекта", groups = {"Smoke", "Regression"})
    public void checkDeletingProject() {
        loginPage.openPage()
                .isOpened()
                .login(user, password)
                .waitTillOpened()
                .deleteProject("Qase");
        assertThat(projectPage.getProjectsList())
                .doesNotContain("Qase");
    }

    @Test(testName = "Проверка создания проекта", groups = {"Smoke", "Regression"})
    public void checkCreateProject() {
        loginPage.openPage()
                .isOpened()
                .login(user, password)
                .waitTillOpened()
                .createProject("Test");
        assertThat(projectPage.getProjectsList())
                .contains("Test");
    }

    @Test(testName = "Проверка добавления чек-листа", groups = {"Smoke", "Regression"})
    public void checkCreatingCheckList() {
        loginPage.openPage()
                .isOpened()
                .login(user, password)
                .waitTillOpened()
                .createProject("Test")
                .openProject("Test")
                .isProjectOpened()
                .createSuit("Test")
                .addCheckList("Successful Login", "Login with empty fields", "Check creating project");
        assertThat(projectPage.getCheckList())
                .contains("Successful Login", "Login with empty fields", "Check creating project");
    }
}
