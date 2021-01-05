# Purchase Data Documentation

## Bank Service
### Setup guide:
#### DB: Postgres
* spring.datasource.url=jdbc:postgresql://127.0.0.1:5432/prepaid_app
* spring.datasource.username=postgres
* spring.datasource.password=postgres
  
#### Authencation: basic authentication
* authentication.username=app-user - User to using bank service
* authentication.password=admin@123456 - Password to using bank service
  

* 3rd.authentication.username=bank-abc - User to call 3rd service
* 3rd.authentication.password=admin@123456 - User to call 3rd service

### Swagger URL:
* http://localhost:8080/swagger-ui.html - We can test using swagger ui

## 3rd Service
### Setup guide:
#### Authencation: basic authentication
* authentication.username=bank-abc - User to using 3rd service
* authentication.password=admin@123456 - Password to using 3rd service