##Project

Multi-module application with
- api-gateway
- customer-kyc-api

## Commands to run the application


## Running the Application
### Running on Docker
run run-all.sh script.It will do all the dirty work for you(start all necessary applications and open swagger-ui on your default browser)

before running you need to give it access rights use the command below

                chmod -R 777 run-all.sh

then run the script using
  
                 ./run-all.sh

The above script will do the following steps
- mvn clean install 
- build images(mysql image,getaway and user-api)
- start docker compose
- and expose applications 
- gateway actuator  `http://localhost:8000/actuator`
- gateway user api via gateway   `http://localhost:8000/user/swagger-ui.html`
- customer-kyc-api  actuator on `http://localhost:9009/actuator`
- customer-kyc-api  swagger on `http://localhost:9009/swagger-ui.html`
- After completing everything browser will be opened pointing to swagger-ui for documentation

####The application will wait for 40 seconds then it will display swagger-ui on your favourite browser

## without Docker
This steps needs you to change the following in application.yml file under customer-kyc api.change profile to Local

                  spring:
                      profiles:
                          active: docker
Then under local profile add your local database password,username ,url and port then run the following to create  a jar file

- build and testing

                mvn clean install

- then run the following command to start the application f

               ./mvnw spring-boot:run


The above command will spin up all the necessary services exposing endpoints on (via gateway)

               http://localhost:8000/user/swagger-ui.html



## Additional technologies/framework used
- Test Containters for testing
- SpringRestDocs for api documentation
- JUnit5,AssertJ,Mockito for testing,assertions and mcoking
- Docker for containerazation
- Spring actuator for production metrics

What needs to be done given more time/Production ready feature
- spring config server - for configuration files
- Test for error documentation in `AccountReportingResourceUTest` and `DeactivateAccountResourceUTest`
- business logic to validate if user account already exist based on given rules(account checking rules).Only one was provided(Name validation).
- The following was not implemented:
    - Delete account : the following steps were going to be used if it was to be implemented.
    - create an enum(AccountStatus with Active and Deleted),so when account is created it will be having an Active state so that on deactivation/delete we simply change status from ACTIVE to DELETED
    - Edit Account: is the same as create account only,however it will update all non field from the user request
    - create a dockerfile to copy springrestdocs document and host it on centos