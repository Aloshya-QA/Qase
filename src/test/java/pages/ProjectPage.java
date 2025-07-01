package pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.Keys;
import org.testng.Assert;

import java.util.ArrayList;
import java.util.List;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;

@Log4j2
public class ProjectPage {

    public ProjectPage openPage() {
        log.info("Opening ProjectPage...");
        open("/projects");

        return this;
    }

    public ProjectPage isOpened() {
        try {
            $(byText("Create new project")).shouldBe(visible);
            log.info("ProjectPage is opened");
        } catch (Exception e) {
            log.error(e.getMessage());
            Assert.fail("ProjectPage isn't opened");
        }

        return this;
    }

    public ProjectPage createProject(String projectName) {
        log.info("Creating project '{}'", projectName);
        if (!getProjectsList().contains(projectName)) {
            $(byText("Create new project")).click();
            $("#project-name").setValue(projectName);
            $(byText("Create project")).click();
            log.info("Project '{}' is created", projectName);
        } else {
            log.info("A project with that name already exists.");
        }

        return this;
    }

    public ProjectPage deleteProject(String projectName) {
        log.info("Delete project '{}'", projectName);
        if (getProjectsList().contains(projectName)) {
            $(byText(projectName))
                    .ancestor("tr")
                    .find("button[aria-label='Open action menu']")
                    .click();
            $("[data-testid=remove]").click();
            $x("//span[text()='Delete project']").click();
        } else {
            Assert.fail("Project '" + projectName + "' not found!");
        }

        return this;
    }

    public List<String> getProjectsList() {
        log.info("Getting project names...");
        refresh();

        $(byText("Loading...")).shouldBe(visible);
        $(byText("Loading...")).shouldNotBe(visible);

        if ($(byText("Looks like you don’t have any projects yet.")).isDisplayed()) {
            log.info("Projects not found!");

            return new ArrayList<>();
        } else {
            log.info("Project names: {}", $$x("//a//img").attributes("alt"));

            return $$x("//a//img").attributes("alt");
        }
    }

    public ProjectPage openProject(String projectName) {
        log.info("Opening project '{}'", projectName);
        $(byText(projectName)).click();

        return this;
    }

    public ProjectPage isProjectOpened() {
        try {
            $(byText("Create new suite")).shouldBe(visible);
            log.info("Project is opened");
        } catch (Exception e) {
            log.error(e.getMessage());
            Assert.fail("Project isn't opened");
        }

        return this;
    }

    public ProjectPage createSuit(String suitName) {
        log.info("Create suit '{}'", suitName);
        $(byText("Create new suite")).click();
        $("#title").setValue(suitName);
        $(byText("Create")).click();

        return this;
    }

    public ProjectPage addCheckList(String... testsName) {
        log.info("Add Check List '{}'", (Object) testsName);
        $("input[placeholder='+ Create quick test']").click();
        for (String testName : testsName) {
            $("input[placeholder='Test case title']")
                    .sendKeys(testName, Keys.ENTER);
            $("input[placeholder='Test case title']").shouldBe(visible);
        }

        return this;
    }

    public List<String> getCheckList() {
        log.info("Getting Check List...");
        refresh();
        $("input[placeholder='+ Create quick test']").shouldBe(visible);
        ElementsCollection ids = $$x("//div[@id='suitecases-container']//a");
        List<String> testsName = new ArrayList<>();
        for (SelenideElement id : ids) {
            testsName.add($x(String.format(
                    "//a[text()='%s']/parent::div/parent::div/following-sibling::div", id.getText())).getText());
        }

        return testsName;
    }
}
