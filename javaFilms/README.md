# WorkProjects

Osvaldo Andres Martinez


### javaFilms :film_projector:

- Exercise for DDD
- Using SakilaDb
- Entities + Dto
- Services
- Catalogue
- Testing (MockTest, DataJpaTest, SpringBootTest).


 Microservices: :desktop_computer:
 
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

 Documentation and Tools: :hammer_and_wrench:
 
- Swagger

 SpringCloud: :cloud:
 
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

- Five tests skkiped not implemented yet.
- Test Coverage 80,6% (lombok methods lower the percentage)



![Tests](/files/javaFilm_testsCoverage_v3.jpg)
---

