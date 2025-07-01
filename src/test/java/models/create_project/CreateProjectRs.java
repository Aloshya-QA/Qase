package models.create_project;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CreateProjectRs {

    @SerializedName("status")
    @Expose
    public Boolean status;
    @SerializedName("result")
    @Expose
    public Result result;
}