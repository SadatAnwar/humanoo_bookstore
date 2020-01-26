# HUMANOO coding challenge

##  Objective 1: Please explain in just a few words...

---
### 1.  How would you access a *NIX server remotely in order to debug a problem?

```
SSH into the machine if that’s possible to look around (mainly logs). But I would also simply
ensure my logs are exported to a central logging infrastructure, something like graylog or ELK  
and use that to inspect my service (without having to ssh into it)  
```

### 2. How would you version your application?

```
Semantic versioning (samver https://semver.org/) is a industry standerd and widely accepted
versioning scheme.
Software is versioned as XX.YY.ZZ
1. Where XX is called the Major version 
  eg: version 1.yy.zz will not be compatible with 2.xx.yy as the bump in major version
      indicates incompatibility 
2. YY represents the minor version. 
  eg: If new features are added, but no breaking changes made, such that software 
      written using 1.5.0 will work fine with 1.6.0 
3. ZZ is called the bug-fix. This is bumped when only bugfixes are done, no additional features
added. 

Samver also ensures you can easily compare versions of software by simply looking at the 
version number, and can already expect certain things. Often times suffix like SNAPSHOT or
RELEASE are added to indicate bleeding edge or release candidates. 

**NOTE**: I hope this question was not about GIT version control? 
```

### 3. How do you deliver your application to your team and for deployment?

```
Application deployment in 2019 (2020) should be done using Docker images (unless there is a
very very good reason not to). Docker ensures reproduceability of behaviour and gurantees
execution context. Docker images can easily be deployed using Kubernetes or other cloud
provider specific offerings like AWS Elastic Bean Stalk or Google AppEngine. 
```

#### 4. If you would have to implement an authorisation / authentication system, what kind of patterns you would choose?

```
Using OpenId Connect 1.0 is an industry standart, it also works very well with OAuth2.0 (as
both use JWTs). Use of JWTs makes authentication and authorization very straight forward and
can work for most cases. 
```



------

## Objective 2: Task implementation

### Requirements:

The solution uses `docker-compose` to simplify the deployment process. You will therefore need to have `docker` and `docker-compose` installed on the test machine. This also simplifies all other requirements, in that you only need docker, the rest is automatically pulled based on requirements.

To install `docker`  and `docker-compose` follow the steps for the required OS on the [official page](https://docs.docker.com/compose/install/)



### Notes about the solution

1. The solution is build using Spring Boot V 2.2.2.RELEASE
2. It has dependencies on Spring Jpa for database access
3. Depends on Spring security for securing the `POST`  `/api/books` endpoint to ensure authorised users can add books
4. Uses Spring actuator to enable actuator endpoints like `/actuator/health` which would be nice to have in a destributed setup
5. Uses [Flyway](https://flywaydb.org/) for databse migration
6. Connectes to a postgres db (that is started up by docker and is also configured in the docker-compose.yaml)
7. Uses [Testcontainers](https://www.testcontainers.org/) to spawn a Postgres db during integration tests. This approach means the integration tests are a bit slower than would be if H2 were used, but ensures that the integration test uses the same db used in production. Has numerous benefits. 
8. Uses Junit4 and Mockito for testing and mocking during tests.



#### Starting the solution

Once you have `docker` and `docker-compose` installed all you need to do is 

```bash
$ docker-compose build
...

$ docker-compose up
```



This should start both the Postgres container and the bookstore api container. The compose.yaml also ensures the bookstore-api container can connect to the postgres container. The first time you run it, it might take a few minutes to download all dependencies and build the containers, but subsequent builds should be significantly faster as most of the reuseable data is going to be cached. 

It is possible to run the solution without docker, but this will need a little more configuration. 

1. Since the solution relies on postgres, you will need to ensure there is a postgres db accessible. 

2. To allow the solution to connect to this PSql db, some ENV variables will need to be set

   1. `export DB_URL=jdbc:postgresql://localhost:5432/postgres`
   2. `export DB_USERNAME=postgres`
   3. `export DB_PASSWORD=xxxxxxx`

   NOTE: The user should have access to create schema, and tables. (The application will create 2 schemas. One for the application itself called `bookstore` and another for flyway called `flywaymigration`)

3. Once you have those env variables setup, you can run

   ```bash
   $ mvn spring-boot:run
   ```

   This should start the application and connect to the postgres instance defined by the env variables 

### Using the application

Once the application is up and running, it will be accessible via `localhost:8080`. An easy way to check if its running is to try `GET` `localhost:8080/actuator/health`

You can use [Postman](https://www.getpostman.com/) to test the API

this should return a json result 

```json
{
  "status": "UP"
}
```

```bash
curl --request GET \
  --url http://localhost:8080/actuator/health \
  --header 'content-type: application/json'
```



### List all books in the store

​	To get a list of all the books in the store

**NOTE**: This API supports pagination. 

```
localhost:8080/api/books?size=1&page=0
```

`GET` `/api/books`

```bash
	curl --request GET \
  --url http://localhost:8080/api/books \
  --header 'content-type: application/json'
```



result will look like 

```json
{
  "content": [
    {
      "id": 1,
      "isbn": "ISBN-121231231231",
      "name": "Harry Potter book 1",
      "author_name": "J.K. Rowling",
      "categories": [
        "Children",
        "Fiction"
      ],
      "created_at": "2020-01-25T23:07:32.248028",
      "updated_at": "2020-01-25T23:07:32.248028"
    }
  ],
  "pageable": {
    "sort": {
      "unsorted": true,
      "sorted": false,
      "empty": true
    },
    "offset": 0,
    "page_number": 0,
    "page_size": 10,
    "paged": true,
    "unpaged": false
  },
  "last": false,
  "total_pages": 1,
  "total_elements": 1,
  "size": 1,
  "number": 0,
  "number_of_elements": 1,
  "first": true,
  "sort": {
    "unsorted": true,
    "sorted": false,
    "empty": true
  },
  "empty": false
}
```



###  Add a book to the store

The following `cURL` command will add a new book to the bookstore. 

In order to add a book to the store, the request will need httpBasic authentication, with username=admin and password=admin. (Naturally this is not very secure but is used here just as a example.) 

```shell
curl --request POST \
  --url http://localhost:8080/api/books \
  --header 'authorization: Basic YWRtaW46YWRtaW4=' \
  --header 'content-type: application/json' \
  --data '{
    "isbn": "ISBN-121231231231",
    "name": "Harry Potter book 4",
    "authorName": "J.K. Rowling",
    "categories": [
      "Children"
    ]
  }'
```

The result will contain the newly created book with its Id populated from the db

```json
{
  "id": 40,
  "isbn": "ISBN-121231231231",
  "name": "Harry Potter book 4",
  "authorName": "J.K. Rowling",
  "categories": [
    "Children"
  ],
  "createdAt": "2020-01-26T00:43:20.581",
  "updatedAt": "2020-01-26T00:43:20.642"
}
```



### Delete book from the store

Sample cURL for deleting a book from the bookstore

```bash
curl --request DELETE \
  --url http://localhost:8080/api/books/1 \
  --header 'authorization: Basic YWRtaW46YWRtaW4=' \
  --header 'content-type: application/json'
```



------

### Add a new category

Sample cURL request for adding a new category

```bash
curl --request POST \
  --url http://localhost:8080/api/categories \
  --header 'authorization: Basic YWRtaW46YWRtaW4=' \
  --header 'content-type: application/json'
  --data '{
    "name": "new category"
  }'
```



### Delete a category

Sample cURL request to delete a category. 

**NOTE:** For a category to be successfuly be deleted, it should not be attached to any book

```bash
curl --request DELETE \
  --url http://localhost:8080/api/categories/1 \
  --header 'authorization: Basic YWRtaW46YWRtaW4=' \
  --header 'content-type: application/json'
```



------

## Objective 3

#### 6.1

 Frontend needs to show the latest access time of a resource. 

1. One way to do this, (the naive way) would be to modify the entity each time it is accessed. I.e when a client does a `GET` `/api/books/{id}` one could modify a field in the entity called `lastAccessedAt` and set it to the current time (also check the updated time is always newer than the exsisting `lastAccedAt`). This however means that the read of a resource is not linked to a write, this will lead to lots of writes on the db and will also reduce speed for a read. 
2. Another way of decoupling the read from the write is to use an asycn setup, each time a client access the resource, a message is published indicating the access time of the resource. A consumer to this message can then update the `lastAccesedAt` from the message. This ensures the updating of `lastAccesedAt` is independent of reading. This would mean, depending on the messaging system, some delay in updating the `lastAccesedAt`. This method adds another advantage, incase the store receives very high access traffic, the messaging system could merge access updates, and update the `lastAccesedAt` at only fixed intervals, (every second or every 2 seconds) this would significantly reduce the write load on the db, and still allow a decent level of service. 

#### 6.2

Since option 1 is valid only for systems that experience very low traffic, and even then its not a nice idea, I'd go with option 2. Have an async setup, publish a message about access and then consume the message to write the `lastAccesedAt`. 

Depending on the remaining architecture there are 2 options I see.

1. Using Akka actors. 

   Treat each entity (book) as an actor, and everytime it is accessed, publish a `resourceAccessed` message for that actor. The actor consumes this message and then updates the state in the db. 

2. Using a messageQ (ApacheKafka, rabbitMq)

   Most destributed systems already have a message queue in place for interservice communication, I'd piggy back on this system, each time the entity is acced, publish a `resourceAccessed` message and then in a seperate consumer consume the message. This consumer can also be a nice place to batch updates and reduce the write load on the db. 

I would go with option 2, as that is very easy to implement if all the pieces are already in place (which is the case for most systems)



#### 6.3

I would use a message queue to solve this problem. 

The cons:

1. If the message queue dies or is having trouble, clients will not see the latest `lastAccesedAt`, this could be fine for some cases and the system would be running in a `degraded` state, but still able to serve traffic.
2. There is always going to be some delay as the message is published, and then consumed. Depending on the application this could be acceptable behaviour. 

The Pros:

1. The decoupling of updating `lastAccesedAt` from reading means higher read throughput, so the system can handle significantly more traffic

2. The solution allows batching the updates to `lastAccesedAt` and depending on the load of the service, this could mean magnitudes time less writes.

3. The system will be an "eventually consistant" system, i.e if there are bottle necks in the message queue, the updates to `lastAccesedAt` will be delayed but will eventually catch up once the system resumes normal operation. 

4. The publishing of `resourceAccessed` message, means it will be easy to extend the functionality of the consumer to add more logic, like maybe update other parts of the systme or raise notifications. 

5. This approach is more modular, and so has numerous binifits that most modular systems have. Like function isolation, easily replace parts or extend logic, etc.

   

