# Tutorial: Spring Boot REST API with API Key Auth & Azure SQL
This simple API written in Java connects to a SQL database hosted on Microsoft Azure and returns info on characters from The Simpsons.
## Table of Contents:
1. Directory Structure of Project
2. Annotations Used in Project
3. Environment Variables (*Plus: Eclipse IDE Setup*)
4. The Filter Chain Pattern (*Security in Spring Boot*)
5. Why is the Repository Class Empty? (*Spring Data JPA*)
6. Hibernate and the `CustomPhysicalNamingStrategy` Config 

## 1. Directory Structure of Project:
```
📁/simpsons-list
├─ 📄 pom.xml (dependency list)
├─ 📁/src
│  ├─ 📁/main
│  │  ├─ 📁/java
│  │  │  └─ 📁/com
│  │  │     └─ 📁/simpsonsfans
│  │  │        └─ 📁/simpsons_list (⭐ root)
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
│  │     ├─ 📄 application.properties (🌐 environment vars, setup)
│  │     └─ 📁/static
│  └─ 📁/test
│     └─ 📁/java
│        └─ 📁/com
│           └─ 📁/simpsonsfans
│              └─ 📁/simpsons_list
│                 └─ 📄 MySpringBootApplicationTests.java
│
│
│ --------------------------------------------------------------------
│
│  /* 🪶 Maven Wrapper Stuff */
├─ 📁/.mvn
│  └─ 📁/wrapper
│     └─ 📄 maven-wrapper.properties
├─ 📄 mvnw (🍏 macOS executable that runs the Maven Wrapper)
└─ 📄 mvnw.cmd (🪟 Windows executable that runs the Maven Wrapper)
```

## 2. Annotations Used in Project:
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

## 3. Environment Variables (Plus: Setting Them Up in Eclipse IDE)
If your Spring Boot API connects to a SQL database, then you're probably providing it with some sensitive information like database usernames and passwords. Because we don't want to hardcode or commit and expose this sensitive data, it's better to safely configure these as `environment variables`.

You'll notice that in the `application.properties` file we're referencing system variables like `${SIMPSONS_KEY}`, `${SIMPSONS_USERNAME}`, and so on. This is because we want the Java application to search in its local running environment to pluck out these variables. Generally, it is best to set these variables in your operating system's environment using `export SIMPSONS_KEY="asdfasdfasdf"` and so on.

However, if you are using Eclipse IDE to develop and locally run this app, you may need to manually tell Eclipse what environment variables to run with, because it has a hard time seeing your OS variables. To do so, do the following:
```
Run > Run Configurations > Environment
```
Then, manually add each variable and value to tell Eclipse what to substitute into your `application.properties` file.

## 4. The Filter Chain Pattern (Security in Spring Boot):
This API implements what is called the "filter chain" pattern. It works by adding a filter to incoming requests before they can hit the API's controller. The flow of what happens when the API receives a new request is as follows:
1. The API receives an HTTP request.
2. **SecurityConfig.java** implements a chain of commands to execute before any controller gets reached. One of the instructions says to instantiate a new `AuthenticationFilter` class instance.
3. **AuthenticationFilter.java** receives all of the info about the HTTP request and uses a try/catch statement in an attempt to instantiate a new `ApiKeyAuthentication` object and grant it to the request. The `ApiKeyAuthentication` class is a user-made class that extends `AbstractAuthenticationToken` - a separate class that is native to Spring Security. In order to perform the key-checking logic that it needs to do, the filter class imports the `AuthenticationService.java` class and calls `getAuthentication()`.
4. **AuthenticationService.java** holds the "business logic" code used for handling if the key string matches, etc. This `@Service` class needs to read from the system's `environment variables` to fetch and inject our secret API key. The reason there are two separate fields for storing the environment's API key is that we need to use the `@Value` annotation from Spring Boot to inject a value from `application.properties` into our current class file. You can't use `@Value` on a `static` field - but we cant access it within the `getAuthentication()` method if it's not static, hence the `makeApiKeyStatic()` method annotated with `@PostConstruct`. The goal of the `getAuthentication()` method that `AuthenticationFilter.java` calls is to return a successful `ApiKeyAuthentication.java` class; if it cannot do this, it returns a `BadCredentialsException` class instead.
5. **ApiKeyAuthentication.java** is a class model that works the same way as your driver's license: it holds information about what your permissions are, and has a few methods to call them up. Only instantiated if the request provides the correct key.
6. If an `ApiKeyAuthentication` class is instantianted, the appropriate API controller will be reached and will respond with the requested data.

## 5. Why is the Repository Class Empty? (Spring Data JPA)
The repository layer of the application (*sometimes called the Data Access Layer*) is responsible for making connections to the SQL database and executing queries.

In this particular app, the `SimpsonsCharacterService.java` class calls upon this repository interface to handle executing the actual SQL queries on the SQL database when our controller is executed. By having our `SimpsonsRepository.java` class extend `JpaRepository` (*provided via* `Spring Data`) we can use built-in SQL methods like `findAll()` to auto-generate simple SQL queries. By passing in type arugments of `<SimpsonsCharacter, UUID>` to the `JpaRepository` interface, we're telling our repository interface that the data will be formatted as `SimpsonsCharacter`, using a `UUID` as IDs.

In other words, this empty `SimpsonsRepository` interface exists to set the database data type as `SimpsonsCharacter.java`, and extends JPA Repository to provide access to built-in methods from `JpaRepository/Spring Data JPA`.

## 6. Hibernate and the `CustomPhysicalNamingStrategy` Config 
Hibernate is an Object-Relational Mapper (ORM) that maps Java classes to database tables. It takes the entity class models you're using in your API and maps them to whatever table or database you're accessing. Importantly, it works together with `JpaRepository` when it generates SQL querries. 

By using annotations like `@Table` and `@Column` within your entity model class files, you tell Hibernate how to map data found in the database to your Java models. However, you need to make sure that the fields in your entity model match the columns in your SQL database. By default, Hibernate uses a naming strategy wherein an entity class model named `SimpsonsCharacter` gets converted to a table name of `simpsons_character`; a class field named `FirstName` gets mapped to a column name of `first_name`, and so on.

To bypass this functionality, you can tell Hibernate how it should name the columns and tables in its SQL querries by overriding its native functions. These `@Override` methods can be found in `/config/CustomPhysicalNamingStrategy.java`. Taking a look at the override for `toPhysicalTableName()` as an example, we can see that the config now tells Hibernate to just return the class model field name as is, instead of making it lowercase and adding an underscore.
