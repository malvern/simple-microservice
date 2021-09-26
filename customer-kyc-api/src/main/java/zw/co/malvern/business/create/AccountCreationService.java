package zw.co.malvern.business.create;

import zw.co.malvern.api.create.UserAccountRequest;
import zw.co.malvern.utils.message.BasicResponse;

@FunctionalInterface
public interface AccountCreationService {
    BasicResponse createUserAccount(UserAccountRequest userAccountRequest);
}
