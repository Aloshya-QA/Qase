package adapters;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import models.create_project.CreateProjectRq;
import models.create_project.CreateProjectRs;

import static io.restassured.RestAssured.given;

public class ProjectAPI {

    static String token = "6fd456105bd532e708412aa30adb53b18f1ea089b851adc26d19f998f8d0dff7";

    static Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();

    static RequestSpecification spec = given()
            .log().all()
            .contentType(ContentType.JSON)
            .header("Token", token);

    public static CreateProjectRs createProject(CreateProjectRq rq) {
        return
                spec
                        .body(gson.toJson(rq))
                        .when()
                        .post("https://api.qase.io/v1/project")
                        .then()
                        .log().all()
                        .statusCode(200)
                        .extract()
                        .as(CreateProjectRs.class);
    }

    public static void deleteProject(String code) {
        spec
                .when()
                .delete("https://api.qase.io/v1/project/" + code)
                .then()
                .log().all()
                .statusCode(200);

    }
}
