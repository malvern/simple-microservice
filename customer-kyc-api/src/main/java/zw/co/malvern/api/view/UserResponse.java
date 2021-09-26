package zw.co.malvern.api.view;

import lombok.Data;
import lombok.experimental.SuperBuilder;
import zw.co.malvern.utils.message.BasicResponse;


@Data
@SuperBuilder
public class UserResponse extends BasicResponse {
    private String accountId;
    private String title;
    private String name;
    private String surname;
    private String dateOfBirth;
    private String jobTitle;
}
