package zw.co.malvern.business.create;

import zw.co.malvern.api.create.UserAccountRequest;
import zw.co.malvern.domain.User;
import zw.co.malvern.repository.UserRepository;
import zw.co.malvern.utils.enums.Title;
import zw.co.malvern.utils.exceptions.AccountException;
import zw.co.malvern.utils.message.BasicResponse;

import java.time.LocalDateTime;

import static zw.co.malvern.utils.dates.DateUtil.systemDateTimeProvider;

public class AccountCreationServiceImpl implements AccountCreationService {
    private final UserRepository userRepository;

    public AccountCreationServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public BasicResponse createUserAccount(UserAccountRequest userAccountRequest) {
        validateString(userAccountRequest.getName(),"Failed to create account.user name cannot be empty");
        userRepository.save(convertUserAccountRequest(userAccountRequest));
        return BasicResponse.builder().narrative("account successfully created").success(true).build();
    }

    private User convertUserAccountRequest(UserAccountRequest userAccountRequest) {
        final User user = new User();
        user.setCreationDate(systemDateTimeProvider());
        user.setDateOfBirth(userAccountRequest.getDateOfBirth());
        user.setName(userAccountRequest.getName());
        user.setSurname(userAccountRequest.getSurname());
        user.setJobTitle(userAccountRequest.getJobTitle());
        user.setTitle(Title.valueOf(userAccountRequest.getTitle().toUpperCase()));
        return user;
    }

    private void validateString(String field,String exceptionMessage){
        if(field == null||field.isEmpty())
            throw new AccountException(exceptionMessage);
    }

}
