package zw.co.malvern.api.update;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class EditUserRequest {
    private String accountId;
    private String title;
    private String name;
    private String surname;
    @ApiModelProperty(value = "Date of Birth",example = "20-11-1989", notes = "use format dd-MM-yyyy",required = true)
    private String dateOfBirth;
    private String jobTitle;
}
