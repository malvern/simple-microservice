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
import zw.co.malvern.api.create.UserAccountRequest;
import zw.co.malvern.api.update.EditUserRequest;
import zw.co.malvern.api.update.UpdateAccountResource;
import zw.co.malvern.business.update.AccountUpdateService;
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

@AutoConfigureRestDocs
@WebMvcTest(controllers = {UpdateAccountResource.class})
public class UpdateAccountResourceUTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private AccountUpdateService accountUpdateService;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {

        objectMapper = new ObjectMapper();
    }

    @AfterEach
    void tearDown() {
        verify(accountUpdateService, times(1)).editAccount(any(EditUserRequest.class));
    }

    @Test
    @DisplayName("Edit user Account")
    void givenEditUserRequest_whenEditAccount_shouldReturnSuccessResponse() throws Exception {
        given(accountUpdateService.editAccount(any(EditUserRequest.class)))
                .willReturn(basicResponse());
        executeUpdateRequest(editUserRequest(),basicResponse(),HttpStatus.OK).andDo(document("{ClassName}",
                responseFields(fieldWithPath("narrative").type("string")
                                .description("explain response meaning"),
                        fieldWithPath("success").type("boolean")
                                .description("response status")))).andReturn();

    }

    @Test
    @DisplayName("attempt to update account")
    void attemptToUpdateAccountWithInvalidRequestShouldReturnErrorResponse() throws Exception {
        given(accountUpdateService.editAccount(any(EditUserRequest.class)))
                .willThrow(new AccountException("Failed to update account"));
        executeUpdateRequest(editUserRequest(), ErrorResponse.builder()
                .errorMessage("Failed to update account").narrative("Account update failed")
                .build(), HttpStatus.BAD_REQUEST).andDo(document("{ClassName}",
                responseFields(fieldWithPath("narrative").type("string")
                                .description("explain response meaning"),
                        fieldWithPath("success").type("boolean").description("response status"),
                        fieldWithPath("errorMessage").type("String").description("Present only if error occurs")
                ))).andReturn();
    }

    private ResultActions executeUpdateRequest(EditUserRequest editUserRequest, BasicResponse expectedResponse,
                                               HttpStatus httpStatus) throws Exception {
        final String url = "/api/account/update";
        return mockMvc.perform(MockMvcRequestBuilders.patch(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(editUserRequest)))
                .andExpect(status().is(httpStatus.value()))
                .andExpect(jsonPath("narrative").value(expectedResponse.getNarrative()))
                .andExpect(jsonPath("success").value(expectedResponse.isSuccess()))
                .andDo(document("{ClassName}",
                        requestFields(
                                fieldWithPath("accountId").type("string").description("database ID"),
                                fieldWithPath("name").type("string").description("user name"),
                                fieldWithPath("surname").type("string").description("user surname"),
                                fieldWithPath("dateOfBirth").type("string").description("user date of birth"),
                                fieldWithPath("title").type("string").description("user title"),
                                fieldWithPath("jobTitle").type("string").description("user job title"))));
    }


    private BasicResponse basicResponse() {

        return BasicResponse.builder().success(true).narrative("Account successfully updated").build();
    }

    private EditUserRequest editUserRequest() {
        final EditUserRequest userRequest = new EditUserRequest();
        userRequest.setAccountId("12345");
        userRequest.setName("malvern");
        userRequest.setSurname("dongeni");
        userRequest.setDateOfBirth("10-10-1906");
        userRequest.setTitle("CDE");
        userRequest.setJobTitle("Financial Manager");
        return userRequest;
    }

}
