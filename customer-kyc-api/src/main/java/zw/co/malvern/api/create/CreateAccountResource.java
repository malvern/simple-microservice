package zw.co.malvern.api.create;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import zw.co.malvern.api.Documentation;
import zw.co.malvern.business.create.AccountCreationService;
import zw.co.malvern.utils.exceptions.AccountException;
import zw.co.malvern.utils.message.BasicResponse;
import zw.co.malvern.utils.message.ErrorResponse;

@CrossOrigin
@RestController
@RequestMapping("api/account")
@Documentation
public class CreateAccountResource {
    private final AccountCreationService accountCreationService;

    public CreateAccountResource(AccountCreationService accountCreationService) {
        this.accountCreationService = accountCreationService;
    }

    @PostMapping("create")
    public ResponseEntity<BasicResponse> createUserAccount(@RequestBody UserAccountRequest userAccountRequest){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(accountCreationService.createUserAccount(userAccountRequest));
    }

    @ExceptionHandler
    private ResponseEntity<BasicResponse> handleError(Exception exception){
        if (exception instanceof AccountException) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ErrorResponse.builder().errorMessage(exception.getMessage())
                    .narrative("Account creation failed").success(false).build());
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ErrorResponse.builder().errorMessage(exception.getMessage())
                .narrative("Internal error occurred").build());
    }
}
