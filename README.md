# A Good Example of Spring Boot with an API Key Authentication
This very simple API was made in Java with Spring Boot and is secured with an API key.
## The Filter Chain Pattern:
1. The API receives an HTTP request.
2. **SecurityConfig** lists a chain of commands to execute before any controller gets reached. One of the instructions says to instantiate a new `AuthenticationFilter` class instance.
3. The **AuthenticationFilter** tries to create a new `ApiKeyAuthentication` class for the incoming HTTP request by importing the `AuthenticationService` and calling its class method `getAuthentication()`.
4. **AuthenticationService** receives the HTTP request and performs the logic of matching the request header's `x-api-key` value with the API's _actual_ key. If it is unsuccesful it instantiates and returns a `BadCredentialsException`; If the keys match, it creates and returns an instance of the `ApiKeyAuthentication` class.
5. The **ApiKeyAuthentication** class is a class model that works the same way as your driver's license: it holds information about what your permissions are, and has a few methods to call them up.
6. If an `ApiKeyAuthentication` class is instantianted, the appropriate API controller will be reached and will respond with the requested data.
## Project Directory Structure
```
📁/simpsons-list
├─ 📁/src
│  ├─ 📁/main
│  │  ├─ 📁/java
│  │  │  └─ 📁/com
│  │  │     └─ 📁/simpsonsfans
│  │  │        └─ 📁/simpsons_list  ⭐ (Project Root)
│  │  │           ├─ 📄 MySpringBootApplication.java
│  │  │           ├─ 📁/controller
│  │  │           │  └─ 📄 SimpsonsController.java
│  │  │           └─ 📁/model
│  │  │              └─ 📄 SimpsonsCharacter.java
│  │  │           └─ 📁/security
│  │  │              ├─ 📄 ApiKeyAuthentication.java
│  │  │              ├─ 📄 AuthenticationFilter.java
│  │  │              └─ 📄 SecurityConfig.java
│  │  │           └─ 📁/service
│  │  │              └─ 📄 AuthenticationService.java
│  │  └─ 📁/resources
│  │     ├─ 📄 application.properties
│  │     └─ 📁/static
│  └─ 📁/test
│     └─ 📁/java
│        └─ 📁/com
│           └─ 📁/simpsonsfans
│              └─ 📁/simpsons_list
│                 └─ 📄 MySpringBootApplicationTests.java
```