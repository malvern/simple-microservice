package zw.co.malvern.business;


import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import zw.co.malvern.api.create.UserAccountRequest;
import zw.co.malvern.business.create.AccountCreationService;
import zw.co.malvern.business.create.AccountCreationServiceImpl;
import zw.co.malvern.domain.User;
import zw.co.malvern.repository.UserRepository;
import zw.co.malvern.utils.exceptions.AccountException;
import zw.co.malvern.utils.message.BasicResponse;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static zw.co.malvern.TestData.accountCreationResponse;
import static zw.co.malvern.TestData.userAccountRequest;

class AccountCreationServiceUTest {
    @Mock
     private UserRepository userRepository;
    private AccountCreationService accountCreationService;
    private ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);

    @BeforeEach
    void setUp() {
        userRepository = mock(UserRepository.class);
        accountCreationService = new AccountCreationServiceImpl(userRepository);
    }

    @Test
    @DisplayName("create user account")
    void createUserAccountShouldReturnSuccess() {
        BasicResponse result = accountCreationService.createUserAccount(userAccountRequest());
        verify(userRepository).save(userArgumentCaptor.capture());
        User userAccount = userArgumentCaptor.getValue();
        assertThat(userAccount).as("user account to be created").isNotNull();
        assertThat(userAccount.getCreationDate()).as("record creation date").isNotNull();
        assertThat(userAccount.getDateOfBirth()).as("date of birth").isNotNull().isEqualTo("10-10-1906");
        assertThat(userAccount.getJobTitle()).as("job title").isNotNull().isEqualTo("Financial Manager");
        assertThat(userAccount.getName()).as("user name").isNotNull().isEqualTo("malvern");
        assertThat(userAccount.getSurname()).as("surname").isNotNull().isEqualTo("dongeni");
        assertThat(result).as("account creation response")
                .isNotNull()
                .isEqualTo(accountCreationResponse());
    }

    @Test
    @DisplayName("throw exception for empty or null name")
    void givenUserRequestWithInvalidName_shouldThrowAccountException(){
        UserAccountRequest userAccountRequest = userAccountRequest();
        userAccountRequest.setName("");
        Assertions.assertThatThrownBy(()->accountCreationService.createUserAccount(userAccountRequest))
                .isInstanceOf(AccountException.class)
                .hasMessage("Failed to create account.user name cannot be empty");
    }
}
