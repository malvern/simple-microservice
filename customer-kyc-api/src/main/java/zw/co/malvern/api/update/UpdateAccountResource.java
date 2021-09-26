package zw.co.malvern.api.update;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import zw.co.malvern.business.update.AccountUpdateService;
import zw.co.malvern.utils.exceptions.AccountException;
import zw.co.malvern.utils.message.BasicResponse;
import zw.co.malvern.utils.message.ErrorResponse;

@CrossOrigin
@RestController
@RequestMapping("api/account")
public class UpdateAccountResource {
    private final AccountUpdateService updateAccountService;

    public UpdateAccountResource(AccountUpdateService updateAccountService) {
        this.updateAccountService = updateAccountService;
    }

    @PatchMapping("update")
    public ResponseEntity<BasicResponse> editUserAccount(@RequestBody EditUserRequest editUserRequest){
        return ResponseEntity.ok(updateAccountService.editAccount(editUserRequest));
    }

    @ExceptionHandler
    private ResponseEntity<BasicResponse> handleError(Exception exception){
        if (exception instanceof AccountException) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ErrorResponse.builder().errorMessage(exception.getMessage())
                            .narrative("Account update failed").success(false).build());
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ErrorResponse.builder().errorMessage(exception.getMessage())
                        .narrative("Internal error occurred").build());
    }
}
