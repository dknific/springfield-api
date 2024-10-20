# A Good Example of Spring Boot with an API Key Authentication
This very simple API was made in Java with Spring Boot and is secured with an API key.
## Table of Contents:
1. Annotations Used in Project
2. Environment Variables (Plus: Eclipse IDE Setup)
3. The Filter Chain Pattern (Security in Spring Boot)
4. Why is the Repository Class Empty?
5. Hibernate and the CustomPhysicalNamingStrategy Config 
6. Directory Structure of Project

## 1. Annotations Used in Project:
**Used in the Entity Classes:**

**@Table**
- Tells Spring Boot  that this entity maps to this table name in SQL

**@Column**
- Tells Spring Boot that this class field maps to an exact SQL column name

**@Id**
- Tells Spring Boot that this class field represents the Entity's ID within the SQL database

**Used in Controllers:**

**@RestController**
- Makes a Java class serve HTTP requests by automatically returning JSON

**@GetMapping**
- Used within a `@RestController` class to map a given method to a given HTTP verb


**Used in Security Configuration:**

**@Configuration**
- Means "this Java class contains definitions for Spring `beans`", making Spring generate and manage the `beans` appropriately

**@EnableWebSecurity**
- Provides a hook to customize security configurations in Spring

**@Bean**
- Used within a `@Configuration` class to make Spring manage a returned object as a bean in the application


**Used in Service Classes:**

**@Override**
- Indicates that a method is overriding an implementation from its super class

**@Service**
- Marks the class as a `service layer component`

**@Value**
- Injects values from `application.properties` into a class field

**@PostConstruct**
- Tells Spring to execute the annotated method after Spring has finished dependency injection and bean initialization, but before the bean is made available for use.

## 2. Environment Variables (Plus: Setting Them Up in Eclipse IDE)
If your Spring Boot API connects to a SQL database, then you're probably providing it with some sensitive information like database usernames and passwords. Because we don't want to hardcode or commit and expose this sensitive data, it's better to safely configure these as `environment variables`.

You'll notice that in the `application.properties` file we're referencing system variables like `${SIMPSONS_KEY}`, `${SIMPSONS_USERNAME}`, and so on. This is because we want the Java application to search in its local running environment to pluck out these variables. Generally, it is best to set these variables in your operating system's environment using `export SIMPSONS_KEY="asdfasdfasdf"` and so on.

However, if you are using Eclipse IDE to develop and locally run this app, you may need to manually tell Eclipse what environment variables to run with, because it has a hard time seeing your OS variables. To do so, do the following:
```
Run > Run Configurations > Environment
```
Then, manually add each variable and value to tell Eclipse what to substitute into your `application.properties` file.

## 3. The Filter Chain Pattern (Security in Spring Boot):
This API implements what is called the "filter chain" pattern. It works by adding a filter to incoming requests before they can hit the API's controller. The flow of what happens when the API receives a new request is as follows:
1. The API receives an HTTP request.
2. **SecurityConfig.java** implements a chain of commands to execute before any controller gets reached. One of the instructions says to instantiate a new `AuthenticationFilter` class instance.
3. **AuthenticationFilter.java** receives all of the info about the HTTP request and uses a try/catch statement in an attempt to instantiate a new `ApiKeyAuthentication` object and grant it to the request. The `ApiKeyAuthentication` class is a user-made class that extends `AbstractAuthenticationToken` - a separate class that is native to Spring Security. In order to perform the key-checking logic that it needs to do, the filter class imports the `AuthenticationService.java` class and calls `getAuthentication()`.
4. **AuthenticationService.java** holds the "business logic" code used for handling if the key string matches, etc. This `@Service` class needs to read from the system's `environment variables` to fetch and inject our secret API key. The reason there are two separate fields for storing the environment's API key is that we need to use the `@Value` annotation from Spring Boot to inject a value from `application.properties` into our current class file. You can't use `@Value` on a `static` field - but we cant access it within the `getAuthentication()` method if it's not static, hence the `makeApiKeyStatic()` method annotated with `@PostConstruct`. The goal of the `getAuthentication()` method that `AuthenticationFilter.java` calls is to return a successful `ApiKeyAuthentication.java` class; if it cannot do this, it returns a `BadCredentialsException` class instead.
5. **ApiKeyAuthentication.java** is a class model that works the same way as your driver's license: it holds information about what your permissions are, and has a few methods to call them up. Only instantiated if the request provides the correct key.
6. If an `ApiKeyAuthentication` class is instantianted, the appropriate API controller will be reached and will respond with the requested data.

## 4. Why is the Repository Class Empty?
The repository class extends the native JpaRepository class. By default, this class imports a bunch of stuff that's ready-to-go out of the box.

Unless you need to define custom queries, this class on it's own will provide you everything you need to do basic queries to your database.

## 5. Hibernate and the CustomPhysicalNamingStrategy Config 
Hibernate is a library that maps Java classes to database tables. By using annotations like `@Table`, `@Column`, and so on, you are telling Hibernate how to map and auto-generate SQL queries.

By default, Hibernate uses a naming strategy where a table named `SimpsonsCharacter` gets mapped to `simpsons_character`; a field named `FirstName` gets mapped to `first_name`, and so on.

To bypass this functionality, you can tell physically tell Hibernate what you would like it to auto-generate. These configurations can be found in the `/config/CustomPhysicalNamingStrategy.java` file.
## 6. Directory Structure of Project:
```
📁/simpsons-list
├─ 📁/src
│  ├─ 📁/main
│  │  ├─ 📁/java
│  │  │  └─ 📁/com
│  │  │     └─ 📁/simpsonsfans
│  │  │        └─ 📁/simpsons_list  ⭐ (Project Root)
│  │  │           ├─ 📄 SimpsonsCharacterListApplication.java
│  │  │           ├─ 📁/config
│  │  │           │  └─ 📄 CustomPhysicalNamingStrategy.java
│  │  │           ├─ 📁/controller
│  │  │           │  └─ 📄 SimpsonsController.java
│  │  │           ├─ 📁/model
│  │  │           │  └─ 📄 SimpsonsCharacter.java
│  │  │           ├─ 📁/security
│  │  │           │  ├─ 📄 ApiKeyAuthentication.java
│  │  │           │  ├─ 📄 AuthenticationFilter.java
│  │  │           │  └─ 📄 SecurityConfig.java
│  │  │           └─ 📁/service
│  │  │              ├─ 📄 AuthenticationService.java
│  │  │              └─ 📄 SimpsonsCharacterService.java
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