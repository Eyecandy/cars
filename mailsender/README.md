## Mail sender 

### How to run 


#### default

first do:  ```mvn clean package``` \
run from cmd line: ```mvn spring-boot:run```  \
OR \
run jar ```java -jar mailsender-0.0.1-SNAPSHOT.jar ``` 

### local

first do:  ```mvn clean package``` \
run from cmd line: ```mvn spring-boot:run``` \
OR \
run jar ```java -jar mailsender-0.0.1-SNAPSHOT.jar  --spring.profiles.active=local```


### prod

first do:  ```mvn clean package``` \

run from cmd line: ```spring-boot:run "-Dspring-boot.run.jvmArguments=-DMAIL_USERNAME=<INSERT MAIL> -DMAIL_PASSWORD=<INSERT PSW> "``` \

run jar ```java -jar mailsender-0.0.1-SNAPSHOT.jar  --spring.profiles.active=prod --MAIL_USERNAME=INSERT_MAIL --MAIL_PASSWORD=INSERT_PSW ```

