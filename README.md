# WorkProjects

Osvaldo Andres Martinez

## General ReadMe

General Readme for all packages and folders.

---


### demo :atom:

- First demo with comments.
- Git usage.
- Tests (With Fail tests!)
- DniValidator
- Entities
- Repository (JPA)
- Validators
- Dtos
- CoreEntities
- CoreRepositories
- Services
- ServicesImplementations
- DataJpaTest + TestEntityManager
- MockBean
- Controllers
- Resources (Actor CRUD)
- Controller Advices
- TestResources
- Spring Rest + HATEOAS
- Swagger(Not running..)
- Eureka
- SpringCloud
- OpenFeign
- ExternalProxies

---


### javaFilms :film_projector:

- Exercise for DDD
- Using SakilaDb
- Entities + Dto
- Services
- Catalogue
- Testing (MockTest, DataJpaTest, SpringBootTest).


 **Microservices:** :desktop_computer:
 
- ActorResource
- ActorResourceTest
- CategoryResource
- CategoryResourceTest
- FilmResource
- FilmResourceTest
- CatalogueResource
- CatalogueResourceTest
- LanguageResource
- LanguageResourceTest
- ExternalResources

 **Documentation and Tools:** :hammer_and_wrench:
 
- Swagger

 **SpringCloud:** :cloud:
 
- SpringCloud
- Eureka
- OpenFeign

:warning: Attention! :warning:

Eureka Discovery Client is running and sending heartbeat! :gear:

You can run Eureka Client in default port 8761.

 PostMan API Tools: :triangular_flag_on_post:

- [Download Postman file](/files/JavaFilms_V1.postman_collection) to import all Api Requests and Tests.
- Every request is prepared for run in http://localhost:8080/**


Note: For run SakilaDb with docker:

`docker run -d --name mysql-sakila -e MYSQL_ROOT_PASSWORD=root -p 3306:3306 jamarton/mysql-sakila`


:warning: Attention! :warning:

- Test Coverage 81,9% (lombok methods lower the percentage)



![Tests](/files/javaFilm_testsCoverage_v4.jpg)


---


### fundamentos (front-end) :basecamp:

- Hello JavaScript!
- Conditionals
- Loops
- Functions
- Lambdas
- Arrays
- Objects
- Prototype
- Global/This
- Classes
- Modules (import/export)
- Calculator


**React:** :atom:

- Component Demo
- Contador
- React Calculator
- Muro (with fetch)
- Formulario
- Catalogo
- 2e2 - Pruebas de entorno

---

### javafilmsfront (front-end) :electron:

Web Front-End for manage JavaFilms Catalog. Made with React. :atom:

View Readme -> (https://github.com/o-andres-m/JavaForWork/tree/main/javafilmsfront#readme)

---

### lotes-batch :robot:

- Processor for insert CSV data to DB
- Processor for create CSV data from DB
- Processor for create XML data CSV
- Processor for create CSV data from EXTERNAL API


---

### demo-web :computer:


- Server with Web front
- Actor controllers (view, add, edit, delete)
- Films controllers (view, add, edit, delete) - Short Version

Defaults for use:

```
user: admin
password: admin
```

---


### gildedRose (kata :martial_arts_uniform:): [GildedRose Repository](https://github.com/emilybache/GildedRose-Refactoring-Kata)

- Exercise for testing and refactoring.

---


### contactos :busts_in_silhouette:

- API REST for manage Contacts with MongoDb

---

### ms.apigateway :building_construction:

- Gateway for Services:

```
http://localhost:8080/  <- eureka
http://localhost:8080/javafilms  <- javaFilms
http://localhost:8080/contactos-service  <- contactos
http://localhost:8080/demos  <- demoService
http://localhost:8080/search  <- gateway to Google
```


---

### ms.eureka :cloud:

- Eureka Microservices Register

```
http://localhost:8761

```


---
