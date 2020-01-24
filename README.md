# HUMANOO coding challenge

##  Objective 1: Please explain in just a few words...

---
### 1.  How would you access a *NIX server remotely in order to debug a problem?

```
SSH into the machine if that’s possible to look around (mainly logs). But I would also simply ensure my logs are exported to a central logging infrastructure, something like graylog or ELK  and use that to inspect my service (without having to ssh into it)  
```

### 2. How would you version your application?

```
Samver is a industry standerd and widely accepted versioning scheme. 
Software is versioned as XX.YY.ZZ
1. Where XX is called the Major version 
  eg: version 1.yy.zz will not be compatible with 2.xx.yy as the bump in major version
      indicates incompatibility 
2. YY represents the minor version. 
  eg: If new features are added, but no breaking changes made, such that software 
      written using 1.5.0 will work fine with 1.6.0 
3. ZZ is called the bug-fix. This is bumped when only bugfixes are done, no additional features added. 

Samver also ensures you can easily compare versions of software by simply looking at the version number, and can already expect certain things. Often times suffix like SNAPSHOT or RELEASE are added to indicate bleeding edge or release candidates. 

**NOTE**: I hope this question was not about GIT version control? 
```

### 3. How do you deliver your application to your team and for deployment?

```
Application deployment in 2019 (2020) should be done using Docker images (unless there is a very very good reason not to). Docker ensures reproduceability of behaviour and gurantees execution context. Docker images can easily be deployed using Kubernetes or other cloud provider specific offerings like AWS Elastic Bean Stalk or Google AppEngine. 
```

#### 4. If you would have to implement an authorisation / authentication system, what kind of patterns you would choose?

```
Using OpenId Connect 1.0 is an industry standart, it also works very well with OAuth2.0 (as both use JWTs). Use of JWTs makes authentication and authorization very straight forward and can work for most cases. 
```



------

## Objective 2: Task implementation

### Requirements:

The solution uses `docker-compose` to simplify the deployment process. You will therefore need to have docker installed on the test machine. This also simplifies all other requirements, in that you only need docker, the rest is automatically pulled based on requirements.





###  Add a book to the store

The following `cURL` command will add a new book to the bookstore. 

```shell
curl --request GET \
  --url http://localhost:8080/books \
  --header 'content-type: application/json' \
  --data '{
    "isbn": "ISBN-121231231231",
    "name": "Harry Potter book 3",
    "authorName": "J.K. Rowling",
    "categories": [
      "Children"
    ]
  }'
```
