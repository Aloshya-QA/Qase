package pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.SetValueOptions;
import org.jspecify.annotations.Nullable;
import org.openqa.selenium.Keys;
import org.testng.Assert;

import java.util.ArrayList;
import java.util.List;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;

public class ProjectPage {

    public void openPage() {
        open("/projects");
    }

    public ProjectPage waitTillOpened() {
        $(byText("Create new project")).shouldBe(visible);
        return this;
    }

    public ProjectPage createProject(String projectName) {
        $(byText("Create new project")).click();
        $("#project-name").setValue(projectName);
        $(byText("Create project")).click();
        return this;
    }

    public ProjectPage deleteProject(String projectName) {
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
        refresh();
        $x("//a//img").shouldBe(visible);
        return $$x("//a//img").attributes("alt");
    }

    public ProjectPage openProject(String projectName) {
        $(byText(projectName)).click();
        return this;
    }

    public ProjectPage isProjectOpened() {
        $(byText("Create new suite")).shouldBe(visible);
        return this;
    }

    public ProjectPage createSuit(String suitName) {
        $(byText("Create new suite")).click();
        $("#title").setValue(suitName);
        $(byText("Create")).click();
        return this;
    }

    public ProjectPage addCheckList(String... testsName) {
        $("input[placeholder='+ Create quick test']").click();
        for (String testName : testsName) {
            $("input[placeholder='Test case title']")
                    .sendKeys(testName, Keys.ENTER);
            $("input[placeholder='Test case title']").shouldBe(visible);
        }

        return this;
    }

    public List<String> getCheckList() {
        refresh();
        $("input[placeholder='+ Create quick test']").shouldBe(visible);
        ElementsCollection ids = $$x("//div[@id='suitecases-container']//a");
        List<String>  testsName = new ArrayList<>();
        for (SelenideElement id : ids) {
           testsName.add($x(String.format(
                   "//a[text()='%s']/parent::div/parent::div/following-sibling::div", id.getText())).getText());
        }
        return testsName;
    }
}
