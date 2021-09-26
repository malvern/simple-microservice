package zw.co.malvern.integration;

import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import zw.co.malvern.api.view.UserResponse;
import zw.co.malvern.utils.message.BasicResponse;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static zw.co.malvern.TestData.accountCreationResponse;
import static zw.co.malvern.TestData.userAccountRequest;
import static zw.co.malvern.integration.ApiConstants.buildUrl;

@Disabled
@ActiveProfiles("dev")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AccountCreationITest {
    @LocalServerPort
    private int port;
    private String createAccount;
    private String viewUserAccount;

    private TestRestTemplate testRestTemplate;

    @BeforeEach
    void setUp() {
        createAccount = buildUrl(port, "/api/account/create");
        viewUserAccount =  buildUrl(port,"/api/account/view/account-id/{accountId}");
        testRestTemplate = new TestRestTemplate();
    }

    @Test
    @Order(1)
    @DisplayName("create user account")
    void createUserAccountShouldReturnSuccessResponse() {
        ResponseEntity<BasicResponse> response = testRestTemplate.postForEntity(createAccount, userAccountRequest(),
                BasicResponse.class);
        assertThat(response).as("create account response").isNotNull()
                .isEqualTo(ResponseEntity.status(HttpStatus.CREATED).body(accountCreationResponse()));
    }

    @Test
    @Order(2)
    @DisplayName("view use account")
    void viewUserAccountShouldReturnSuccessResponse() {
        ResponseEntity<UserResponse> response = testRestTemplate.getForEntity(viewUserAccount,
                UserResponse.class, "1");
        assertThat(response).as("view user account").isNotNull()
                .isEqualTo(ResponseEntity.status(HttpStatus.OK).body(accountCreationResponse()));
    }

}
