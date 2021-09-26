package zw.co.malvern.api.view;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import zw.co.malvern.api.Documentation;
import zw.co.malvern.business.view.AccountReportingService;

@CrossOrigin
@RestController
@RequestMapping("api/account")
@Documentation
public class AccountReportingResource {
    private final AccountReportingService accountReportingService;

    public AccountReportingResource(AccountReportingService accountReportingService) {
        this.accountReportingService = accountReportingService;
    }


    @GetMapping("view/account-id/{accountId}")
    public ResponseEntity<UserResponse> viewAccountByStatus(@PathVariable String accountId){
        return ResponseEntity.ok().body(accountReportingService.viewUserAccount(accountId));
    }
}
