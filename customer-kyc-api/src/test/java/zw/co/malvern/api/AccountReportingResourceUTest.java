package zw.co.malvern.api;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import zw.co.malvern.api.view.AccountReportingResource;
import zw.co.malvern.business.view.AccountReportingService;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static zw.co.malvern.TestData.userAccountResponse;

@AutoConfigureRestDocs
@WebMvcTest(controllers = {AccountReportingResource.class})
public class AccountReportingResourceUTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private AccountReportingService reportingService;


    @Test
    @DisplayName("view all user accounts")
    void viewUserAccounts() throws Exception {
        final String url = "/api/account/view/account-id/{accountId}";
        given(reportingService.viewUserAccount(anyString())).willReturn(userAccountResponse());
        mockMvc.perform(RestDocumentationRequestBuilders.get(url, "12345"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("narrative").value(userAccountResponse().getNarrative()))
                .andExpect(jsonPath("success").value(userAccountResponse().isSuccess()))
                .andDo(document("{ClassName}",
                        pathParameters(parameterWithName("accountId").description("user account id")),
                        responseFields(fieldWithPath("narrative").type("string").description("explain response meaning"),
                                fieldWithPath("success").type("boolean").description("response status"),
                                fieldWithPath("surname").type("string").description("user surname"),
                                fieldWithPath("dateOfBirth").type("string").description("user date of birth"),
                                fieldWithPath("title").type("string").description("user title"),
                                fieldWithPath("jobTitle").type("string").description("user job title"),
                                fieldWithPath("name").type("string").description("user name"),
                                fieldWithPath("accountId").type("string").description("user account id"))
                ))
                .andReturn();
        verify(reportingService, times(1)).viewUserAccount(anyString());
    }


}
