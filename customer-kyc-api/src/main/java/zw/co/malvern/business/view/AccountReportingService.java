package zw.co.malvern.business.view;

import zw.co.malvern.api.view.UserResponse;

@FunctionalInterface
public interface AccountReportingService {
    UserResponse viewUserAccount(String accountId);
}
