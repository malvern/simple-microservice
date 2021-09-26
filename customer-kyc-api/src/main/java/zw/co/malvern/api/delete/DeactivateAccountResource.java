package zw.co.malvern.api.delete;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import zw.co.malvern.business.delete.AccountDeactivationService;
import zw.co.malvern.utils.message.BasicResponse;

@CrossOrigin
@RestController
@RequestMapping("api/account")
public class DeactivateAccountResource {
    private final AccountDeactivationService accountDeactivationService;

    public DeactivateAccountResource(AccountDeactivationService accountDeactivationService) {
        this.accountDeactivationService = accountDeactivationService;
    }

    @DeleteMapping("deactivate/account-id/{accountId}")
    public ResponseEntity<BasicResponse> deactivateAccount(@PathVariable String accountId){
        return ResponseEntity.status(HttpStatus.OK).body(accountDeactivationService.deactivateAccount(accountId));
    }
}
