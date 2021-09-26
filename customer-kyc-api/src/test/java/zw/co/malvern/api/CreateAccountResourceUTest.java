package zw.co.malvern.api;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;
import zw.co.malvern.api.create.CreateAccountResource;
import zw.co.malvern.api.create.UserAccountRequest;
import zw.co.malvern.business.create.AccountCreationService;
import zw.co.malvern.utils.exceptions.AccountException;
import zw.co.malvern.utils.message.BasicResponse;
import zw.co.malvern.utils.message.ErrorResponse;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static zw.co.malvern.TestData.accountCreationResponse;
import static zw.co.malvern.TestData.userAccountRequest;

@AutoConfigureRestDocs
@WebMvcTest(controllers = {CreateAccountResource.class})
public class CreateAccountResourceUTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private AccountCreationService accountCreationService;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
    }

    @AfterEach
    void tearDown() {
        verify(accountCreationService, times(1))
                .createUserAccount(any(UserAccountRequest.class));
    }

    @Test
    @DisplayName("create user account")
    void givenUserDetails_whenCreatingAccount_shouldReturnSuccess() throws Exception {
        given(accountCreationService.createUserAccount(any(UserAccountRequest.class)))
                .willReturn(accountCreationResponse());
        executeRequest(userAccountRequest(), accountCreationResponse(), HttpStatus.CREATED)
                .andDo(document("{ClassName}",
                responseFields(fieldWithPath("narrative").type("string")
                                .description("explain response meaning"),
                        fieldWithPath("success").type("boolean")
                                .description("response status")
                ))).andReturn();
    }

    @Test
    @DisplayName("attempt to create account with invalid data")
    void attemptToCreateAccountWithInvalidDataShouldReturnErrorResponse() throws Exception {
        given(accountCreationService.createUserAccount(any(UserAccountRequest.class)))
                .willThrow(new AccountException("Failed to create account.user name cannot be empty"));
        ErrorResponse expectedResponse = ErrorResponse.builder()
                .errorMessage("Failed to create account.user name cannot be empty")
                .narrative("Account creation failed").success(false).build();
        executeRequest(userAccountRequest(), expectedResponse, HttpStatus.BAD_REQUEST)
                .andDo(document("{ClassName}",
                        responseFields(fieldWithPath("narrative").type("string")
                                        .description("explain response meaning"),
                                fieldWithPath("success").type("boolean")
                                        .description("response status"),
                                fieldWithPath("errorMessage").type("String")
                                        .description("Present only if error occurs"))))
                .andReturn();
    }

    private ResultActions executeRequest(UserAccountRequest userAccountRequest, BasicResponse expectedResponse,
                                         HttpStatus httpStatus) throws Exception {
        final String url = "/api/account/create";
        return mockMvc.perform(MockMvcRequestBuilders.post(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userAccountRequest)))
                .andExpect(status().is(httpStatus.value()))
                .andExpect(jsonPath("narrative").value(expectedResponse.getNarrative()))
                .andExpect(jsonPath("success").value(expectedResponse.isSuccess()))
                .andDo(document("{ClassName}",
                        requestFields(fieldWithPath("name").type("string").description("user name"),
                                fieldWithPath("surname").type("string").description("user surname"),
                                fieldWithPath("dateOfBirth").type("string").description("user date of birth"),
                                fieldWithPath("title").type("string").description("user title"),
                                fieldWithPath("jobTitle").type("string").description("user job title"))));
    }





}
