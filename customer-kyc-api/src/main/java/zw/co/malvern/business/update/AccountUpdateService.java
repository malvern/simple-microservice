package zw.co.malvern.business.update;

import zw.co.malvern.api.update.EditUserRequest;
import zw.co.malvern.utils.message.BasicResponse;

public interface AccountUpdateService {
    BasicResponse editAccount(EditUserRequest editUserRequest);
}
