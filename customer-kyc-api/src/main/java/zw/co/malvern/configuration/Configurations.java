package zw.co.malvern.configuration;

import lombok.Builder;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import zw.co.malvern.business.create.AccountCreationService;
import zw.co.malvern.business.create.AccountCreationServiceImpl;
import zw.co.malvern.business.delete.AccountDeactivationService;
import zw.co.malvern.business.delete.AccountDeactivationServiceImpl;
import zw.co.malvern.business.update.AccountUpdateService;
import zw.co.malvern.business.update.AccountUpdateServiceImpl;
import zw.co.malvern.business.view.AccountReportingService;
import zw.co.malvern.business.view.AccountReportingServiceImpl;
import zw.co.malvern.domain.DomainMarkerInterface;
import zw.co.malvern.repository.RepositoryMarkerInterface;
import zw.co.malvern.repository.UserRepository;

@Configuration
@EnableJpaRepositories(basePackageClasses = {RepositoryMarkerInterface.class})
@EntityScan(basePackageClasses = {DomainMarkerInterface.class})
public class Configurations {

    @Bean
    public AccountReportingService accountReportingService(final UserRepository userRepository){
        return new AccountReportingServiceImpl(userRepository);
    }

    @Bean
    public AccountDeactivationService accountDeactivationService(){
        return new AccountDeactivationServiceImpl();
    }

    @Bean
    public AccountUpdateService accountUpdateService(){
        return new AccountUpdateServiceImpl();
    }
    @Bean
    public AccountCreationService accountCreationService(final UserRepository userRepository){
        return new AccountCreationServiceImpl(userRepository);
    }
}
