package zw.co.malvern.business;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import zw.co.malvern.api.view.UserResponse;
import zw.co.malvern.business.view.AccountReportingService;
import zw.co.malvern.business.view.AccountReportingServiceImpl;
import zw.co.malvern.repository.UserRepository;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static zw.co.malvern.TestData.getUserAccount;

class AccountReportingServiceUTest {
    @Mock
     private UserRepository userRepository;
    private AccountReportingService accountReportingService;

    @BeforeEach
    void setUp() {
        userRepository = mock(UserRepository.class);
        accountReportingService = new AccountReportingServiceImpl(userRepository);
    }

    @Test
    @DisplayName("retrieve user account")
    void shouldRetrieveUserAccount() {
        given(userRepository.findById(anyLong())).willReturn(Optional.of(getUserAccount()));
        UserResponse userResponse = accountReportingService.viewUserAccount("12345");
        assertThat(userResponse).as("user response").isNotNull();
        assertThat(userResponse.isSuccess()).as("response status").isNotNull().isTrue();
        assertThat(userResponse.getNarrative()).as("response narrative")
                .isNotNull().isEqualTo("User Account found");
        verify(userRepository,times(1)).findById(anyLong());
    }
    @Test
    @DisplayName("attempt to retrieve non existent user account")
    void attemptToRetrieveNonExistingAccountShouldReturnNoUserResponse() {
        given(userRepository.findById(anyLong())).willReturn(Optional.empty());
        UserResponse userResponse = accountReportingService.viewUserAccount("12345900");
        assertThat(userResponse).as("user response").isNotNull();
        assertThat(userResponse.isSuccess()).as("response status").isNotNull().isFalse();
        assertThat(userResponse.getNarrative()).as("response narrative")
                .isNotNull().isEqualTo("Account not found");
        verify(userRepository,times(1)).findById(anyLong());
    }
}
