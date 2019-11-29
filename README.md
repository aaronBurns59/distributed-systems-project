# distributed-systems-project
A project which contains a gRPC password service and a REST user account service

# Part 1 gRPC
## How to run gRPC-Part1
mvn compile  
java -jar passwordService.jar

# Part 2 UserAccountService
Adapted from:
[https://howtodoinjava.com/dropwizard/tutorial-and-hello-world-example/](https://howtodoinjava.com/dropwizard/tutorial-and-hello-world-example)
## How to run UserAccountService-Part2 from intellij IDE
mvn package  
java -jar target/UserAccountService-Part2-0.0.1-SNAPSHOT.jar server userApiConfig.yaml

hosted on: localhost:9000

# Swaggerhub API
[https://app.swaggerhub.com/apis/GMIT7/UserAPI/1.1#free](https://app.swaggerhub.com/apis/GMIT7/UserAPI/1.1#free)

