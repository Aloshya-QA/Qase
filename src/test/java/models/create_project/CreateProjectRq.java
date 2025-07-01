package models.create_project;

import com.google.gson.annotations.Expose;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateProjectRq {

    @Expose
    String title;
    @Expose
    String code;
    @Expose
    String description;
    @Expose
    String access;
    @Expose
    String group;
}
