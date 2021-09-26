package zw.co.malvern;

import zw.co.malvern.api.create.UserAccountRequest;
import zw.co.malvern.api.view.UserResponse;
import zw.co.malvern.domain.User;
import zw.co.malvern.utils.enums.Title;
import zw.co.malvern.utils.message.BasicResponse;

public class TestData {
    public static UserAccountRequest userAccountRequest() {
        final UserAccountRequest userAccountRequest = new UserAccountRequest();
        userAccountRequest.setName("malvern");
        userAccountRequest.setSurname("dongeni");
        userAccountRequest.setDateOfBirth("10-10-1906");
        userAccountRequest.setTitle("CDE");
        userAccountRequest.setJobTitle("Financial Manager");
        return userAccountRequest;
    }

    public static BasicResponse accountCreationResponse() {
        return BasicResponse.builder().narrative("account successfully created").success(true).build();
    }
    public static UserResponse userAccountResponse(){
        return UserResponse.builder().narrative("User Account found")
                .accountId("1234").dateOfBirth("10-10-1906").jobTitle("Farming").name("moses")
                .surname("ian").title("ras").success(true).build();
    }

    public static User getUserAccount(){
        final User user = new User();
        user.setName("malvern");
        user.setSurname("dongeni");
        user.setDateOfBirth("10-10-1906");
        user.setTitle(Title.CDE);
        user.setJobTitle("Financial Manager");
        return user;
    }
}
