package zw.co.malvern.api;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;
import zw.co.malvern.api.delete.DeactivateAccountResource;
import zw.co.malvern.business.delete.AccountDeactivationService;
import zw.co.malvern.utils.message.BasicResponse;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Disabled
@AutoConfigureRestDocs
@WebMvcTest(controllers = {DeactivateAccountResource.class})
public class DeactivateAccountResourceUTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private AccountDeactivationService accountDeactivationService;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
    }

    @Test
    @DisplayName("deactivate account")
    void deactivateCustomerAccountShouldReturnSuccess() throws Exception {
        final String url = "/api/account/deactivate/account-id/{accountId}";
        given(accountDeactivationService.deactivateAccount(anyString())).willReturn(BasicResponse.builder().build());
        mockMvc.perform(MockMvcRequestBuilders.patch(url,"12345")
                        .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isCreated())
                .andExpect(jsonPath("narrative").value(basicResponse().getNarrative()))
                .andExpect(jsonPath("success").value(basicResponse().isSuccess()))
                .andDo(document("{ClassName}",
                        responseFields(fieldWithPath("narrative").type("string").description("explain response meaning"),
                                fieldWithPath("success").type("boolean").description("response status")),
                        requestFields(fieldWithPath("accountId").type("string").description("user account-id"))
                ))
                .andReturn();
        verify(accountDeactivationService,times(1)).deactivateAccount(anyString());
    }
    private BasicResponse basicResponse() {
        return BasicResponse.builder().build();
    }

}
