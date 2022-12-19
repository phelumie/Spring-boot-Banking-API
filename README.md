# Spring-boot-Banking-API
A Rest API developed with Spring Boot that allows users to perform banking transactions over the internet.

## Table of contents
* **Getting Started**
  * [What is a banking management system all about?](https://github.com/phelumie/Spring-boot-Banking-API#what-is-a-banking-management-system-all-about)
* [**Why this project?**](https://github.com/phelumie/Spring-boot-Banking-API#why-this-project)
* [Technologies](https://github.com/phelumie/Spring-boot-Banking-API#technologies)
* **[System Requirements](https://github.com/phelumie/Spring-boot-Banking-API#system-requirements)**
  * [Functional Requirements](https://github.com/phelumie/Spring-boot-Banking-API#functional-requirements)
  * [Non-Functional Requirements](https://github.com/phelumie/Spring-boot-Banking-API#non-functional-requirements)
    * [Security](https://github.com/phelumie/Spring-boot-Banking-API#security)
    * [Data Integrity](https://github.com/phelumie/Spring-boot-Banking-API#data-integrity)
    * [Performance](https://github.com/phelumie/Spring-boot-Banking-API/blob/master/README.md#performance)
    * [Availability](https://github.com/phelumie/Spring-boot-Banking-API/blob/master/README.md#availability)
    * [Usability](https://github.com/phelumie/Spring-boot-Banking-API/blob/master/README.md#usability)
  * [Software Requirements](https://github.com/phelumie/Spring-boot-Banking-API/blob/master/README.md#software-requirements)

* **[High-Level Design](https://github.com/phelumie/Spring-boot-Banking-API/blob/master/README.md#high-level-design)**
  * [Architecture](https://github.com/phelumie/Spring-boot-Banking-API/blob/master/README.md#architecture)
    * [Why Microservices?](https://github.com/phelumie/Spring-boot-Banking-API/blob/master/README.md#why-microservices)
  * [Capacity Estimation](https://github.com/phelumie/Spring-boot-Banking-API/blob/master/README.md#capacity-estimation) 
    * [Traffic estimation](https://github.com/phelumie/Spring-boot-Banking-API/blob/master/README.md#traffic-estimation)
      * [Customer service](https://github.com/phelumie/Spring-boot-Banking-API/blob/master/README.md#customer-service)
      * [Transaction Service](https://github.com/phelumie/Spring-boot-Banking-API/blob/master/README.md#transaction-service)
      * [Loan Service](https://github.com/phelumie/Spring-boot-Banking-API/blob/master/README.md#loan-service)
      * [Issue service](https://github.com/phelumie/Spring-boot-Banking-API/blob/master/README.md#issue-service)
      * [Employee Service](https://github.com/phelumie/Spring-boot-Banking-API/blob/master/README.md#employee-service)
  * [Memory management](https://github.com/phelumie/Spring-boot-Banking-API/blob/master/README.md#memory-management)
  * [Communication Protocol](https://github.com/phelumie/Spring-boot-Banking-API/blob/master/README.md#communication-protocol)
  * [Messaging Queues](https://github.com/phelumie/Spring-boot-Banking-API/blob/master/README.md#message-queues)
  * [Choice of Database](https://github.com/phelumie/Spring-boot-Banking-API/blob/master/README.md#choice-of-database)
  * [Caching](https://github.com/phelumie/Spring-boot-Banking-API/blob/master/README.md#caching)
    * [Cache Pattern(Cache aside)](https://github.com/phelumie/Spring-boot-Banking-API/blob/master/README.md#cache-patterncache-aside)
  * [Rate limiting](https://github.com/phelumie/Spring-boot-Banking-API/blob/master/README.md#rate-limiting)
    * [Benefits of using a rate limiter](https://github.com/phelumie/Spring-boot-Banking-API/blob/master/README.md#benefits-of-using-a-rate-limiter) 
    * [Design](https://github.com/phelumie/Spring-boot-Banking-API/blob/master/README.md#design)
    * [Algorithm](https://github.com/phelumie/Spring-boot-Banking-API/blob/master/README.md#algorithm)
     * [Token bucket](https://github.com/phelumie/Spring-boot-Banking-API/blob/master/README.md#token-bucket)
       * [Implementation](https://github.com/phelumie/Spring-boot-Banking-API/blob/master/README.md#implementation)
     * [Fixed window counter](https://github.com/phelumie/Spring-boot-Banking-API/blob/master/README.md#fixed-window-counter)
       * [Implementation](https://github.com/phelumie/Spring-boot-Banking-API/blob/master/README.md#implementation-1)
  * [Monitoring and Logging](https://github.com/phelumie/Spring-boot-Banking-API/blob/master/README.md#monitoring-and-logging)
    * [JVM](https://github.com/phelumie/Spring-boot-Banking-API/blob/master/README.md#jvm)
    * [Distributed Tracing](https://github.com/phelumie/Spring-boot-Banking-API/blob/master/README.md#distributed-tracing)
  * [Security](https://github.com/phelumie/Spring-boot-Banking-API#security-1)
    * [Authentication and Authorization](https://github.com/phelumie/Spring-boot-Banking-API#authentication-and-authorization)
    * [Brute Force Attack](https://github.com/phelumie/Spring-boot-Banking-API#brute-force-attack)
    * [SQL Injection](https://github.com/phelumie/Spring-boot-Banking-API#sql-injection)
    * [Denial of Service (DoS)](https://github.com/phelumie/Spring-boot-Banking-API#denial-of-service-dos)
    * [Cross-Site Scripting (XSS)](https://github.com/phelumie/Spring-boot-Banking-API#cross-site-scripting-xss)
* [**Low-Level Design**](https://github.com/phelumie/Spring-boot-Banking-API#low-level-design)
  * [API-Gateway](https://github.com/phelumie/Spring-boot-Banking-API#api-gateway)
    * [Functions](https://github.com/phelumie/Spring-boot-Banking-API#functions)
  * [Customer Service](https://github.com/phelumie/Spring-boot-Banking-API#customer-service-1)
    * [Functions](https://github.com/phelumie/Spring-boot-Banking-API#functions-1)
    * [Database Design](https://github.com/phelumie/Spring-boot-Banking-API#database-design) 
  * [Transaction Service ](https://github.com/phelumie/Spring-boot-Banking-API#transaction-service-1)
    * [Functions](https://github.com/phelumie/Spring-boot-Banking-API#functions-2)
    * [Database Design](https://github.com/phelumie/Spring-boot-Banking-API#database-design-1)
  * [Loan Service](https://github.com/phelumie/Spring-boot-Banking-API#loan-service-1)
    * [Functions](https://github.com/phelumie/Spring-boot-Banking-API#functions-3)
    * [Credit Score Rating](https://github.com/phelumie/Spring-boot-Banking-API#credit-score-rating)
    * [Loan Worthiness Analysis](https://github.com/phelumie/Spring-boot-Banking-API#loan-worthiness-analysis)
    * [Loan Payment](https://github.com/phelumie/Spring-boot-Banking-API#loan-payment)
    * [Database Design](https://github.com/phelumie/Spring-boot-Banking-API#database-design-2)
  * [Issue Service](https://github.com/phelumie/Spring-boot-Banking-API#issue-service-1)
    * [Functions](https://github.com/phelumie/Spring-boot-Banking-API#functions-4)
  * [Employment Service](https://github.com/phelumie/Spring-boot-Banking-API#employee-service-1)
    * [Functions](https://github.com/phelumie/Spring-boot-Banking-API#functions-5)
    * [Database Design](https://github.com/phelumie/Spring-boot-Banking-API#database-design-3)
* [**Documentation**?](https://github.com/phelumie/Spring-boot-Banking-API#documentation)
* [**Challenges and things i learned from this project**](https://github.com/phelumie/Spring-boot-Banking-API/blob/master/README.md#challenges-and-things-i-learned-from-this-project)

## Why this project?
I have been curious about how the fintech industries work,and how complex the design would be.Also, i wanted to improve my knowledge of backend engineering(both high level and low level) and go in depth with spring boot(security,filters,jvm memory management etc.). What better way than to build an app that encompass everything. I used the microservices architecture to broaden and improve my knowledge. 
<br/>
## What is a banking management system all about?
  The Bank Management System  is a web-based application used for paying financial institutions for the services they provide.<br/>
Depending on the bank, the components of the bank management system may vary, but generally speaking, the system consists of core banking to manage fundamental transactions, loans, mortgages, and payments available via ATM, mobile banking, and branches.

## Technologies
* Java 
* Spring Boot(rest api)
* Spring webflux (reactive programming)
* Maven(build automation tool)
* Tomcat (application server)
* Netty (NIO server)
* Hibernate(ORM)
* Swagger(OPEN API- documentation)
* Mysql(Relational database)
* MongoDb(Nosql database)
* Redis (Caching and rate limiting)
* Mockito (unit testing)
* Junit5 (Integration testing)
* Jmeter (load/performance testing)
* Keycloak (authentication and authorization)
* Spring Security(authorization)
* Spring data JPA(transactions)
* Flyway (database migrations)
* zipkin (distributed tracing)
* Prometheus and grafana (monitoring)
* Resilence4J (circuit breaker)
* rabbitmq(message queue/broker)
* Docker(containerization)
* Kubernetes (orchestration)
* nginx(web server)
* Istio(serivice mesh/mTLS)
* Visio(architecture diagram)

## System Requirements
A bank management system's requirements are based on the business expectations and they give a detailed description of the system behavior.The system's operation must be in accordance with national laws and regulations.In Nigeria,the Corporate Affairs Commission, Central Bank of Nigeria, Securities and Exchange Commission etc.  are the institutions that govern banking activities.

### Functional Requirements
These are broken down into three access levels: Admin Mode, Teller Mode, and Customer Mode. They describe the service that the banking management system must provide.
* Customer:
  * Sign in with login and password.
  * Create one or more accounts.
  * Update personal details.
  * Check balance.
  * View personal history of transactions.
  * Transfer money.
  * Withdraw. 
  * Cash deposit.
  * Debit card request if account active.
  * Borrow loans if meet eligibility requirements.
  * Lodge complaint if any.
  * Receive a welcome email on account setting up.
  * Receive birthday wishes mail on their birthday.     
* Employee/Staff:
  * Sign in with login and password.
  * Register new bank customers.
  * View customer information.
  * Manage customer accounts.
* Admin:
  * Sign in with login and password.
  * View employees,managers and customers details.
  * Add or update bank branch details.
  * Add or update manager details.
  * Demote or delete employees

### Non-Functional Requirements
Non-Functional Requirements are the constraints or the requirements imposed on the system. They outline the software's quality feature. Scalability, maintainability, performance, portability, security, dependability, and many more factors that influence non-functional requirements. 
They help in constructing the security policy of the software system. They ensure good user experience, ease of operating the software, and minimize the cost factor.
Non-Functional Requirements address critical software system quality challenges.<br/>
<br/>
In most cases, determining whether a product satisfies a non-functional criterion or not boils down to a simple yes or no decision.<br/>
  The most crucial non-functional requirements for a bank management system are security, performance, data integrity,usability, and availability.
  
 #### Security
 Bank management systems are well known for being subject to malicious attacks,security is therefore the primary requirement for the system.
Data must be regularly backed up and kept in a secure area, away from the system's  facilities and any access to these data must be from a trusted and secured network.<br/>
Digital files that are stored online and transactions must be encrypted using 128-bit or 256-bit AES encryption standards. To protect against network threats, the system must additionally use firewall software. The system should OWASP compliant.
The system must offer an automated log-out after a certain amount of inactivity and block login attempts after numerous failed tries on the client side(brute force attack).

#### Data Integrity

Data integrity is a concept and process that ensures the accuracy, completeness, consistency, and validity of an organization’s data. The importance of data integrity increases as data volumes continue to increase exponentially.
Given that our system will be highly available i.e we will have multiple database instances(replicas) where we separate reads from the writes. There are many replication techniques,in the process of replication, a read request may come in and we would not want our client to read dirty/stale data. To avoid this me must choose a database that is ACID complaint. Also databases like MySQL, PostgreSQL have operators that manages the replicas in a clustered environment.
Also, concurrency control should be implemeted for each transaction to manage simultaneous processes to execute without conflicts between each other.
Data integrity is essential, if ignored it could cost the organization a lot of money.

#### Performance
The bank management system is a multi-client system that must run a target number of transactions per second without failing and meet response time goals for each client during concurrent calls. To reduce operating expenses, the system must use hardware and energy resources efficiently. So it is necessary to do a performance test/stress test on each service.
#### Availability
System accessibility is required during bank business hours. The system must be accessible 24/7 with little downtime to serve mobile banking app and ATM, averaging 99.999% availability yearly. In order to achieve thus it is required to tune the  jvm  because while the garbage collector(GC) is running all other operations/requests are paused. It is also better to use a better and more efficient GC algorithm like ZGC which reduces pause time.

#### Usability
Customers, tellers, and administrators must all have unique graphical user interfaces provided by the system. All system interfaces must be user-friendly and simple to understand, with helpful cues and signals as well as intuitive workflow. This is especially true for client interfaces, which must be easy for clients to learn and use without any prior understanding of banking terms or regulations.

To increase readability, interfaces must automatically adapt to devices with various screen sizes and support changing typeface size and color scheme.

 
## Software Requirements
After the functional and non-functional needs have been determined, the software requirements specification (SRS), which is a description of the software system that will be developed. Using an on-premise, cloud, or hybrid computing approach will affect the range of programming tools and technologies that can be integrated into the bank management system.

The majority of large-scale financial institutions operate their main banking system on-site, which may be mandated by the legal system's necessity to enable servers that keep personal data on the nation's territory.

The development of the system is based on the following technologies:

 * Servers running Windows Server/Linux OS.
 * Python is required for the data analysis and fraud detection engine, and a scalable programming language enabling multi-threading, such as Java, is required for the backend.
 * Modern front-end frameworks like React/Angular/Svelte for user-interface.
 * Relational DBMS with an engine that supports ACID transactions like MySQL or Oracle RDBMS.
 * NoSQL for unstructed data.

## High-Level Design
### Architecture
This project provides an API Gateway for microservices architecture, and is built on top of reactive Netty and Project Reactor. It is designed to provide a simple, but effective way to route to APIs and address such popular concerns as security, monitoring/metrics, and resiliency.
<br/>
<br/>
![pop-1](https://user-images.githubusercontent.com/67476155/202458238-667aa670-5ee6-4546-bb50-5ee800ea8f87.png)

####  Why Microservices?
A microservices architecture is an approach to building a server application as a set of small services.Each service runs in its own process and communicates with other processes using protocols such as HTTP/HTTPS, WebSockets, or AMQP. Each microservice must be designed individually and be deployed independently. Each microservice implements a particular end-to-end domain or business capability inside a specified context boundary. Finally, each microservice could have a different programming language and data storage technology (SQL, NoSQL) and own its associated domain data model and domain logic (sovereignty and decentralized data management).
<br/>
<br/>
So to answer the question,the purpose of a microservices architecture It offers long-term agility, to put it briefly. Microservices help you build applications based on several independently deployable services, each of which has granular and autonomous lifecycles. This improves maintainability in complicated, massive, and highly scalable systems. Microservices make it easier to test, understand, and maintain application builds with the combination of independent components. Microservices can also help you adapt more quickly to the changing market conditions. Because microservices allow applications to be updated and tested quickly, you can follow market trends and adapt your products faster.
<br/>
<br/>
The best architecture for continuous delivery is one based on microservices. With microservices, each application and the environment it need to function are stored in a different container. Because of this, editing each program in its container eliminates the possibility of affecting any other applications.
Users will have no downtime, troubleshooting will be made simpler, and there won't be any disturbance even if an issue is found.
<br/>
<br/>
Microservices give programmers the resources they need to create software of a higher quality. Each element of an application may reside in its own container with a microservice architecture and be maintained and updated separately. Instead of having to select a single, less-than-ideal language to utilize for everything, developers may construct applications from a variety of components and write each component in the language most suited to performing its specific role. You may improve the quality of your goods by optimizing software right down to the individual application components.

### Capacity Estimation
Lets say we design similar system similar to one of the biggest banks like GTBank
#### Traffic estimation
Monthly Active Users(MAU) =25M
##### Customer service
Daily Active Users(DAU)= 10M
<br/>
We are going to many reads and writes. So breaking it down; we have 5+2=7(average read - write req)
<br/>
Then total req per day= 10M * 7= 70M per day
<br/>
query per sec= 70M/(24 * 3600)= 810query/sec

##### Transaction Service
Daily Active Users(DAU)= 7M
<br/>
Average req per day=2
<br/>
total requests= 7M * 2= 14M per day
<br/>
query per sec= 14M/(24 * 3600)= 162query/sec
##### Loan Service
Daily Active Users(DAU)= 300K
<br/>
Average req per day=2
<br/>
total requests= 300K * 2=600K per day
<br/>
query per sec= 600K/(24 * 3600)= 7query/sec

#### Issue Service
Daily Active Users(DAU)= 7K
<br/>
Average req per day=3
<br/>
total requests= 7K * 3= 21K
<br/>
query per sec= 21K/(24 * 3600)= 1query/sec

#### Employee Service
Daily Active Users(DAU)= 10K
<br/>
Average req per day=2
<br/>
total requests= 10K * 2= 20K
<br/>
query per sec= 20K/(24 * 3600)= 1query/sec

### Memory management
This is the process of allocation and de-allocation of objects.Java does memory management automatically. Java uses an automatic memory management system called a garbage collector(gc). However to improve performance and increase availability, we should tune the jvm to the expected behaviour. If the jvm is not configured or not properly configured, it can lead to drop in availability and performance because the gc can run any time or worst scenario if the gc runs often. This is bad becuase when the gc runs it pauses all activities(no requests are processed), now imagine if it runs often. It is good to choose a better gc algorithm with low pause time. GC algorithm like ZGC and also increase the heap size and set the stack size to desireable result. We can monitor the memory using prometheus and grafana.

### Communication Protocol
All our microservices are in a service mesh(istio) uses the HTTP protocol to communicate but then istio upgrades the protocol to HTTPS. Configuring HTTPS in our microservices leads to tons of configuration which also adds a minute amount of latency. iStio provides us mTLs authentication whhich helps ensure that the traffic is secure and trusted in both directions between a client and server.  Traffic from the client is received by either nginx or istio gateway with TLS termination and then istio encrypts the connection. Below are visualization of the communication:
<br/>
<br/>
![Screenshot (206)](https://user-images.githubusercontent.com/67476155/202754438-82867f4a-66b3-4520-8127-685317e891d1.png)
 <br/>
 <br/>
 ![Screenshot (204)](https://user-images.githubusercontent.com/67476155/202756585-d9f73b46-3fee-481a-a6a1-c2736ea8d07d.png)

### Message Queues
A message queue is  permits communication between separate applications and services. Until the consuming application can digest them, "messages"—packets of data that applications produce for other apps to consume—are stored in message queues in the order they are dispatched. This makes it possible for messages to securely wait until the receiving application is prepared, ensuring that the messages in the message queue are not lost in case of network or receiving application issues. Our architecture is Event driven i.e asyschronous communication using events. Operations like deposits,notifications, loan Analysis, loan withdraw are all handled by events whcih provides scalability and fault tolerance(in which if the service is down, messages are kept till when the service is up).<br/>
<br/>
<br/>
![Screenshot (210)](https://user-images.githubusercontent.com/67476155/202935449-2127747a-613d-483c-9a18-9499de959d79.png)

### Choice of Database
Three major factors determine the type of database to use, which are scale, structure,and query pattern. Our data are structured and can be represented as a table, and we need our transactions to be atomic, consistent, isolated, and durable (ACID), we go with a relational database(RDB). The most commonly used one would be MySQL. Which is also going to be our source of truth, later we can have a dump maybe for metrics analysis but we are fine just having a source of truth. Then for issues service our data is unstructed and we do not need atomicity nor isolation .So choosing a RDB is not neccessary, hence we go for a  DocumentDB like MongoDB because we have a vast variety of attributes in our data and a vast variety of queries. Then for caching we are using Redis.

### Caching
In high-concurrency systems on the Internet, cache is a frequently utilized component. Because it has an additional layer, if it is not utilized properly, such as "remove or update the cache," it can be counterproductive.

#### Cache Pattern(Cache aside)
The application first fetches data from the cache, if it does not get it, it fetches the data from the database, and after success, puts it in the cache(cache miss).
On cache hit, the application fetches data from the cache and returns after fetching.
<br/>
In short,the cache aside pattern is
* If the cache cannot be read, read the database, put the data to the cache, and then try to read from the cache again.
* Update the database first when data has to be changed, and then invalidate (remove) the relevant data from the cache.

To improve the cache hit, we can pre-heat the cache i.e loading the cache with data.

### Rate limiting
One way to control network traffic is by rate restriction. It restricts how frequently someone may carry out a particular action during a predetermined period of time. A rate limiter may be defined as a way to control the rate of traffic sent or received on the network.
<br/>
Spring Cloud Gateway currently provides a Request Rate Limiter, which is responsible for restricting each user to N requests per second.
When using RequestRateLimiter with Spring Cloud Gateway we may leverage Redis. 
#### Benefits of using a rate limiter
* Prevent resource  depletion brought on by Denial of Service (DoS) attacks.
* Reduce cost. Limiting excess request means fewer servers and allocating more resources to high priority APIs.
* Prevent servers from being overloaded.
#### Design
We can choose between two implementation options: client-side and the server-side.
The client side is a little unreliable because bots can simply spoof requests or start attacking the service API directly, which would be bad for us.
In this project we will go with server-side implementation.Then in server-side implementation, we have a choice between two methods:

* Inside an API gateway as middleware
* At our controller endpoints within our application
Again, in this project we will go with the API gateway approach.
#### Algorithm
There are various algorithm for rate limiting but then in this project we will look at Fixed window counter and token bucket algorithm. Both the algorithms are implemented but then fixed window counter is choosen,we can later switch to the other based on performance. The fixed window counter is custom gateway pre filter which limits user access, we can also write it to limit user/clients based on their plans. 
<br/>
##### Token bucket
This algorithm has a centralized bucket host where you take tokens on each request, and slowly drip more tokens into the bucket. If the bucket is empty, it rejects the request.Tokens can be taken from a bucket before some action done. If there are enough tokens, take them and allow an action. Otherwise, decline it. But that’s not all. TokenBucket is refilled with N tokens every second or other time T. It controls amount of actions this way. At the same time, it provides ability for traffic bursts, depending on amount of tokens, of course.
###### Implementation
A Spring Cloud Gateway component by the name of GatewayFilter is used to implement request rate limiting. This filter is built in a specific factory for every instance.Filter is in charge of altering requests and responses before to or following the transmission of the downstream request.
The keyResolver parameter is optional, and the GatewayFilter also accepts implementation-specific parameters (in that case an implementation using Redis reactive). A bean that implements the KeyResolver interface is the parameter keyResolver. You can use a variety of techniques to determine the secret to limiting requests. A bean that implements the KeyResolver interface is the parameter keyResolver. In this project our key resolver will be our keycloak id gotten from  the request header.
##### Fixed window counter
 Fixed window counter works like:
 * For each timeline we create counter and initialize it with zero.
* After each request we increment the counter by 1.
* Once the counter reaches the pre-defined threshold, we can start throwing exception and send 429 Http Status code to the client.
It is memory efficient as we are just storing the count as value and user id(service name + keycloak id) as key and also easy to understand.
###### Implementation
We can go with IP address based counter or User unique key based counter.In general IP blocking is a good practice because the IP is the only identifier that exists for anonymous users but keep in mind that a user can "switch" IPs using online proxy services  on the other hand that maybe multiple users share an identical IP when they use a proxy server.
<br/>
Since our users are authenticated then we limit/block the userId when the user is doing malicious stuff.
Since we increment the counter when a request comes, issue arise in a concurrent environment.
Let’s imagine there are two thread A and B and both have same userId and both have hit our endpoint at the same time when the count was 0. Ideally our count should be 2. But as both have read the count as 0 it would have tried to update it to 1, which was incorrect. Redis comes to solve this problem. Redis will handle the concurrent thread and will only allow one thread to update value at one time since redis is single threaded.

### Monitoring and logging
Monitoring microservices so important because we work in a time where systems are complex, distributed across multiple microservices.Health monitoring is the process of using an application performance monitoring or related tool to gain better insight into all key areas of your app and its performance, allowing you to make better and more informed decisions as development goes on.<br/>
Logging is the practice of managing all of the log data produced by your applications and infrastructure.The cornerstone of security (or protective) monitoring is log collection, which is necessary to comprehend how your systems are being utilized. In the case of a worry or prospective security problem, excellent logging procedures will enable you to reflect back on what happened and comprehend the incident's effects. This is furthered by security monitoring, which actively analyzes log data to search for indications of known attacks or anomalous system behavior. This enables organizations to identify occurrences that could be classified as security incidents and take appropriate action to lessen the damage.
<br/>
To monitor our application logs we can use the EFK OR ELK stack.
#### JVM
Kubernetes  gives us a declarative way to scale our application using pod replication. The replication provides a very convenient way to improve the availability and fault-tolerance of our application. The tools we will analyze in this project, Prometheus with Grafana, give us the opportunity to monitor all the pods in our kubernetes cluster.
There are several tools we can use to monitor JVM-based microservices in the Kubernetes environment. One of the best among them is Prometheus.
Prometheus is a widely used monitoring and time series database. Prometheus offers very efficient storage using a time series database, many integrations, powerful queries using the PromQL query language, great visualization, and alerting. Prometheus is a pull-based monitoring system that actively collects or scrapes monitoring data from the application exposed via the Metrics API.
<br/>
Below is an example of our microservice under performance test.<br/>
<br/>
![Screenshot (207)](https://user-images.githubusercontent.com/67476155/204692263-7967f8a3-1d56-4d02-91b0-2d4610f7f20a.png)
<br/>
<br/>
![Screenshot (208)](https://user-images.githubusercontent.com/67476155/204692282-378f9df0-1cb3-49c1-8cb5-f5aec3a7fb58.png)
<br/>
<br/>
![grafana-monitoring com_d_gCsss9vVz_jvm-micrometer_orgId=1 refresh=5s var-application= var-instance=customer-658c5c6fbf-9cfdq var-jvm_memory_pool_heap=All var-jvm_memory_pool_nonheap=All (1)](https://user-images.githubusercontent.com/67476155/204688766-4b99d270-6408-4700-bb8b-57f5bf0c6b66.png)

#### Distributed Tracing
Distributed tracing makes it possible to see where things are happening. Distributed tracing captures individual units of work, also known as spans, in a distributed system. A great example of distributed tracing is a workflow request, which is a series of activities that are necessary to complete a task.
You can track a transaction throughout a distributed microservices environment using distributed tracing, an observability data source that identifies the precise location of an issue.
<br/>
Distributed tracing helps in the following:
* Informs development teams about the health and status of deployed application systems and microservices
* Identifies irregular behavior that results from scaling automation
* Reviews how the average response times, error frequencies, and other metrics are reflected through the end-user’s experience
* Tracks and records vital statistics on performance with user-friendly dashboards
* Recognizes and addresses the base cause of unexpected issues
We have many tracing tools like zipkin and Jaeger, we will be going with zipkin. We can integrate it with our application and also with our service mesh (istio).<br/>

![Screenshot (99)](https://user-images.githubusercontent.com/67476155/204934196-35e11010-c7cc-4e06-b018-d9308b4e6e88.png)
<br/>
<br/>
<br/>
![Screenshot (100)](https://user-images.githubusercontent.com/67476155/204934230-6e13cdd2-c2dd-4a9c-8251-1b5a35ab6932.png)

### Security
To prevent threats, other malicious tasks, attacks, or programs from reaching the computer's software system, security is one of the most crucial and significant responsibilities.A system is said to be secure if its resources are used and accessed as intended in all circumstances.
#### Authentication and Authorization
Authentication is the process of confirming that a person is who they claim to be in order to establish their identity.
Both the server and the client make use of it. When someone tries to access the information and the server needs to know who is doing so, authentication is used. When he wants to verify that the server is who it says it is, the client uses it.
There are different authentication techniques:
* Password-based authentication(requires username and password)
* 2FA/MFA(requires additional PIN or security questions)
* Single Sign-on(sign-in once and have access to multiple applications)
* Social Authentication (using socials like github,LinkedIn)

<br/>
Authorization is the process of granting someone to do something.It refers to a technique for determining if a user has authorization to utilize a resource or not.
It specifies the types of data and information that a user may access.
In order for the system to know who is accessing the information, authentication and authorization are typically used together.

#### Brute Force Attack
A brute force attack is a hacking technique that makes use of trial and error to break encryption keys, passwords, and login credentials. It is a straightforward but effective strategy for getting unauthorized access to user accounts, company systems, and networks. Until they discover the proper login information, the hacker tries a variety of usernames and passwords, frequently utilizing a computer to test a wide range of combinations.
<br/>
Locking user accounts after a certain number of unsuccessful login attempts in a period of time is crucial. To prevent a brute force assault, rate-limiting must be applied upon login.

#### SQL injection
Database(s) could be destroyed by the code injection technique known as SQL injection.It is one of the most popular methods used to hack websites.By using the input from clients, malicious code is inserted into SQL statements.
<br/>
To protect a web site from SQL injection, you can use SQL parameters i.e parameterized queries.

#### Denial of Service (DoS)
In a DDoS assault, the attacker attempts to make unavailable/disrupt a certain service by sending massive amounts of traffic continuously from numerous end systems. Due to this heavy traffic, the network resources are used to fulfill requests from those fake end systems, making it impossible for a legitimate user to access the resources for their own usage.
We prevent Dos by the following:
* Cloud Mitigation Provider
* Caching
* Rate limiting

#### Cross-Site Scripting (XSS)
According to OWASP, Cross-Site Scripting (XSS) attacks are a type of injection, in which malicious scripts are injected into otherwise benign and trusted websites. XSS attacks occur when an attacker uses a web application to send malicious code, generally in the form of a browser side script, to a different end user. Flaws that allow these attacks to succeed are quite widespread and occur anywhere a web application uses input from a user within the output it generates without validating or encoding it. It also very common in the fintech industry.
<br/>
Infact years ago(2010), twitter had an xss attack in which the vulnerability took advantage of the onmouseover function in JavaScript, which works by executing JavaScript code by simply moving your mouse over some text. 
<br/>
We can prevent xss by framework security(front-end and backend) and also output encoding.
<br/>
 Our primary focus is our backend since all our data and logic resides there, we should protect it all cost not relying on our front-end. To prevent this a custom fiter has been written to filter all responses and replace all malicious code(contents with javascript code) with empty strings.
 
## Low-Level Design
Giving the internal logical design of the actual program code is the aim of a low-level design (LLD). Based on the high-level design, the low-level design is produced. LLD explains class diagrams that show the relationships and methods between classes and program specifications. In order for the programmer to create the program directly from the document, it describes the modules.
When adequate analysis is used to build a low-level design document, the program is made simple to develop as a result. The low-level design document can then be used directly to develop the code, requiring little testing and debugging.
### API-Gateway
The API Gateway is a server(It serves as a reverse proxy). It is a single entry point into a system. The core system architecture is encapsulated by API Gateway. It provides an API that is tailored to each client. It also has other responsibilities such as authentication,rate limiting, circuit breaking,retry mechanism, monitoring, load balancing, caching, request shaping and management, and static response handling.
<br/>
<br/>
     The API Gateway processes each and every request the client makes. The API Gateway then directs requests to the proper microservice.Here, filters were written to perform some operations on the request e.g Caching( local cache and redis),xss and rate limiting.
#### Functions
* Request Processing.
* Authentication
* Caching(implemented using redis  and concurrent hashmap)
* Rate limiting
* Xss response filter
* Circuit breaking(using  resilence4j).
* Load balancing(uses eureka in dev environment,uses kubernetes for service discovery and load balncing in prod environment).
### Customer Service
This is in charge of customer profile and accounts creation.On succesful customer profile creation, we notify the customer asychronously via mail using the sendgrid API.This manages all customers data including account balance. Whenever a debit transaction occurs(withdraw/transfer), the transaction obtains a lock (pessimistic lock) which ensures no concurrent transaction occurs on the customer account.   
<br/>
<br/>
#### Functions
* Creating  customers account(s) and profiles, in which a customer can have more than one account.
* Update customer details
* Debit card Request.
* Customer *GET* requests
* *DELETE* customer account
* Welcome mail message upon account creation.
* A job which runs once daily which check and sends customers happy birthday mail.
#### Database Design
![customer-service](https://user-images.githubusercontent.com/67476155/205658038-56823499-6a08-45fc-9171-21aee92ea082.png)

### Transaction Service
This is where all transaction takes place(deposit,transfer,withdraw). The deposit transaction is handled asychronously,while other transactions are handled with netty non blocking (which makes our thread re-usable and able o serve more requests). This also generate all customers sucessful transactions, be it deposit, withdraw or transfer. If the customer transaction service is unavailale, it retries 3 times and if not still available it returns the appropriate error response with code 503.
#### Functions
* Handles transactions like deposit,withdraw ansd transfer.
* Retries on all transactions if correspondig service is down
* *GET* request on all customer successful depoits.
* *GET* request on all customer failed depoits.
* All customer transactions which is used to generate statement of account.
#### Database Design
![transaction](https://user-images.githubusercontent.com/67476155/205663537-f0580fa8-0fea-4c3d-9952-8d046bd91012.png)

### Loan Service
This service deals with loan application and processing. There are two forms of loan; bank loan and p2p loan. The bank loan means customer requests loan directly from the bank by choosing different available offers. Offers may be different, depending on the seasons.Each offer has a limit and also different monthly interest rate and late payment interest rate. Every customer depending on the account has a loan limit. Therefore, ther requested loan must not be greater than the loan limit.
<br/>
#### Functions
* Loan Request
* Loan Processing,Analysis and Approving.
* Loan Payments.
* A cron job which runs once daily to get all due loans with grace of two days period and then debit them automatically.
<br/>
When a customer requests for a loan, the status of loan is *PENDING*. Then the loan undergoes loan risk analysis, this is divided into two:
* Credit Score Rating
* Loan Worthiness Analysis
#### Credit Score Rating
After much research, i was able to come up with how it is done in the real world. The pictures below will briefly show and explain the criteria used:
<br/>
![Screenshot (196)](https://user-images.githubusercontent.com/67476155/205946839-d98728d0-e94e-4565-b076-63ae2c22197f.png)

![Screenshot (211)](https://user-images.githubusercontent.com/67476155/205946874-356cd461-42ae-4b64-b3a1-e3baf8519621.png)
 
##### Payment History
This checks customers record on how they paid their past loans.By default the grade is 35 points, so on every 30 days late payment 1 point is deducted from the point. For customers requesting for the first time 35 points is given.
##### Debt Ratio
All customers have their credit limit depending on their account type and status.For a customer with an outstanding debt to request loan the current debt shoud be lesser than 30% of the loan limit.  When a customer who stil has an outstanding debt requests for loan, this check is the customers current debit is greater than 30% of loan limit, if so the customer is awarded 0 and if not it is awarded 30 point.
##### Length of Credit History
This is use to check the level of trust. If the customer has been banking for 10 years and above, 10 points is rewarded else 0.
##### Credit mix
This shows that the customer has requested for loan before and he/she is familiar with the terms and condition. For this, the customer is rewarded 10 points.
##### Credit Inquiries
This checks how often the customer has requested for loan in the current year. If less than three (3), it is rewarded 10 points else 0 point.
<br/>

#### Loan Worthiness Analysis
Here an algorithm is written which  checks the credibility and worthiness of the customer. The factors that determines loan worthiness of a customer includes:
* Age: The mminimum required age is 18. If below the loan is *REJECTED*
* Marital Status: If the customer is married, probably the head of the family, 15% of the income is set aside and deducted.
* Monthly income: This is a major factor,the customers income will determine the monthly payments after adding necessary interests. 
* Existing loan: For customers with existing loan, their monthly payment for the loan is deducted from the monthly income.
* Monthly Expenses: The customer submits his monthly expenses and that is deducted from the monthly income. If the amount left after all the deductions is lesser than the calculated  monthly payments, the loan is then *REJECTED*.

If a customer meets all requirements the loan status is set to *UNDER_CONSIDERATION*. The authorized personnel will then review the credit score and loan analysis of the loan requested by the consider, checks for neccessary validation and if then pass the loan is disbursed. The loan status then changes to *ACCEPTED.*

Loan Monthly Payment Formula:
![Mortgage_payment_formula_CxSQ69M (1)](https://user-images.githubusercontent.com/67476155/206303585-923c763a-61f4-444b-865b-89f05ae3fc32.jpg)

#### Loan Payment
After loan disbursment, the customer is expected to start making payments at leasts 30 days after. The customer is given 2 days, after which the monthly payment is deducted automatically from the customer's account. In a case where the customer is behind for months in its payment, a late fee is attracted i.e there is a specific late fee charges on each loan e.g let say the late fee charges is 2.5% of the loan and the customer is behind by 5 months. The interest is then calculated as thus: 2.5% multiply by the principal amount multiply by 5(months behind).
<br/>
Customers can also pay directly to the bank.

#### Database Design
![loan-service](https://user-images.githubusercontent.com/67476155/207426571-9ec8aca8-69c4-4ed5-9010-3e523f94b4d7.png)

### Issue Service
This manages customers issues/complaint. When lodged, it remains in the *PENDING* state until it's addressed and fixed by authorized staffs, which then changes to *FIXED*. It uses a NoSQL db.
#### Functions
* Make complaint
* Review complaint
* Fix complaint
* Check all pending complaint
* Back pressure 

### Employee Service
This service manages employees and admins. It manages creation of new branches,departments etc. API's are roles protected.
Roles like:
* admin
* manager
* coo
* hr
* employee
Moving from top to bottom,  the roles at top can perform the duties attached to lower level roles.
#### Functions
* Register new employee
* Create new branch
* *GET* employee
* *DELETE* employee
* Promote employee to roles like *ADMIN*
* Demote from higher roles to lower.
#### Database Design
![employee-service](https://user-images.githubusercontent.com/67476155/205667199-5f313d7f-ddb8-4739-ab57-a1094db4aed7.png)
            

### Documentation.
Open API Specification is a standard API description format for Rest APIs.Swagger is an open-source tool provided by SmartBear to implement this specification. It helps to design, build, document, and consume Rest APIs.
In this project swagger 3(OPEN API) is implemented to generate our documentation. The api gateway has been configured to generate each microservice documentation.
Below is an example of visualizing our documentation using Swagger UI.
<br/>
![Screenshot (213)](https://user-images.githubusercontent.com/67476155/207915412-98956194-5231-4c3b-9d94-58634f24abf8.png)
<br/>
<br/>
<br/>
![Screenshot (214)](https://user-images.githubusercontent.com/67476155/207915451-26814928-9d38-4c25-9a0c-970049b75f37.png)
To visualize our documentation:
<br/>
<br/>
URL: http://<app-root>/swagger-ui/index.html
<br/>
<br/>
To generate our documentation for each service:
<br/>
<br/>
URL: http://<app-root>/SERVICE-NAME/v3/api-docs

### Challenges and things i learned from this project
The main goal of these project is to improve and fine-tune my knowledge about backend engineering. While designing and implementing some requirements, i encountered some issues, which are:
 * Designing the microservices using domain driven approach
 * Event driven architecture(appropriate use of events to communicate between microservices)
 * Due to lack of experience in the fintech industries, i had to make researches for days and nights especially with loan service. I made tons of researches , journals and articles how the banking industry and loan management system works.
 * Implementing Rate limiting and caching layer.
 * Integrating microsservices with istio. This gave me problem for weeks, i had to spend days studying documentation and reading arcticles os sites like medium and stackoverflow.
<br/><br/>
 Things i learnt from this project includes:
 * Writing idempotent Api's
 * The art of debugging.
 * Hibernate N+1 problem.
 * Databse connection pooling and how it affects performance.
 * Writing hibernate custom validator.
 * Working with cron jobs.
 * Implementing event dricen design with spring boot.
 * Improved my system desgin knowledge especially high level desgin.
 * Decreasing latency and increase in throughput.
 * Jvm memory management and thread optimization.
 * Fault tolerance (circuit breaker,Retries).
 * Database engineering( sql query optimization, indexes and avoiding full table scan).
 * Security best bractices.
 * Monitiring and logging.
 * Deploying microservices in a service mesh like istio and etc.
 <br/>
 I am glad i embarked on this project, it has widen my horizon.
