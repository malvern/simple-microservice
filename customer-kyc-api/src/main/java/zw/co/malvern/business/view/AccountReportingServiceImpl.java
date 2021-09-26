package zw.co.malvern.business.view;

import zw.co.malvern.api.view.UserResponse;
import zw.co.malvern.domain.User;
import zw.co.malvern.repository.UserRepository;

import java.util.Optional;

public class AccountReportingServiceImpl implements AccountReportingService {
    private final UserRepository userRepository;

    public AccountReportingServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserResponse viewUserAccount(String accountId) {
        Optional<User> optionalUser = userRepository.findById(Long.parseLong(accountId));
        if (optionalUser.isEmpty())
            return UserResponse.builder().narrative("Account not found").success(false).build();
        return UserResponse.builder().success(true)
                .narrative("User Account found")
                .title(optionalUser.get().getTitle().name())
                .accountId(String.valueOf(optionalUser.get().getId()))
                .surname(optionalUser.get().getSurname())
                .jobTitle(optionalUser.get().getJobTitle())
                .name(optionalUser.get().getName())
                .dateOfBirth(optionalUser.get().getDateOfBirth())
                .build();
    }
}
