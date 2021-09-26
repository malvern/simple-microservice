package zw.co.malvern.business.delete;

import zw.co.malvern.utils.message.BasicResponse;

public interface AccountDeactivationService {
    BasicResponse deactivateAccount(String accountId);
}
