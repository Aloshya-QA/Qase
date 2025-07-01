package tests.ui;

import io.qameta.allure.Description;
import io.qameta.allure.Step;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;


public class ProjectTest extends BaseTest{

    @Test(testName = "Проверка удаления проекта", groups = {"Smoke", "Regression"})
    @Description("Проверяет, возможность удаления проекта через UI")
    @Step("Удаление проекта")
    public void checkDeletingProject() {
        loginStep.auth(user, password);
        projectPage.deleteProject("Qase");

        assertThat(projectPage.getProjectsList())
                .doesNotContain("Qase");
    }

    @Test(testName = "Проверка создания проекта", groups = {"Smoke", "Regression"})
    @Description("Проверяет, возможность создания проекта через UI")
    @Step("Coздание проекта")
    public void checkCreateProject() {
        loginStep.auth(user, password);
        projectPage.createProject("Test");

        assertThat(projectPage.getProjectsList())
                .contains("Test");
    }

    @Test(testName = "Проверка добавления чек-листа", groups = {"Smoke", "Regression"})
    @Description("Проверяет, что в проект можно добавить чек-лист")
    @Step("Добавление чек-листа в проект")
    public void checkCreatingCheckList() {
        loginStep.auth(user, password);
        projectPage.createProject("Test")
                .openProject("Test")
                .isProjectOpened()
                .createSuit("Test")
                .addCheckList("Successful Login", "Login with empty fields", "Check creating project");

        assertThat(projectPage.getCheckList())
                .contains("Successful Login", "Login with empty fields", "Check creating project");
    }
}
