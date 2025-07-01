package tests.api;

import models.create_project.CreateProjectRq;
import models.create_project.CreateProjectRs;
import org.testng.annotations.Test;

import static adapters.ProjectAPI.createProject;
import static adapters.ProjectAPI.deleteProject;
import static org.assertj.core.api.Assertions.assertThat;

public class ProjectAPITest {

    @Test
    public void checkCreateProject() {
        CreateProjectRq rq = CreateProjectRq.builder()
                .title("QASE")
                .code("QA")
                .description("test")
                .access("all")
                .group("test")
                .build();

        CreateProjectRs rs = createProject(rq);
        assertThat(rs.status).isTrue();
        assertThat(rs.result.code).isEqualTo("QA");

        deleteProject(rs.result.code);
    }
}
