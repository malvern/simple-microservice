package zw.co.malvern.api.create;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class UserAccountRequest {
    @ApiModelProperty(value = "title",example = "MR", notes = "MR",required = true)
    private String title;
    @ApiModelProperty(value = "name",example = "malvern", notes = "name",required = true)
    private String name;
    @ApiModelProperty(value = "surname",example = "dongeni", notes = "surname",required = true)
    private String surname;
    @ApiModelProperty(value = "Date of Birth",example = "20-11-1989", notes = "use format dd-MM-yyyy",required = true)
    private String dateOfBirth;
    @ApiModelProperty(value = "jobTitle",example = "farmer", notes = "jobTitle",required = true)
    private String jobTitle;
}
