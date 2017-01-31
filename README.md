The project stores the key/pair on an in-memory database, using Spring-JPA and HSQLDB.
On an optimal distribution it should be using a more specialized clustered key/value database, like Redis (with disk persistence enabled), Riak, etc...

It uses Gradle over Maven since I consider Gradle much more flexible, less verbose and easier to create non-standard tasks.

In order to be a SCS ([self-contained system](http://scs-architecture.org/)) this project has the frontend embeded as well. 
For an optimal configuration it should have a a closer look on the UI, today it has only some very simple structure to make the user life easier.
It also provides the REST APIs in order to be able to integrate with another systems.
* To generate a new shorten URL = `curl -X POST -d 'url=www.google.com.br' http://localhost:8080/shorten`
* To navigate to the original URL = `curl -X GET http://localhost:8080/{short id}`

It uses the [MurmurHash 3](https://en.wikipedia.org/wiki/MurmurHash#MurmurHash3) to generate a small output from the input URL. 

The application runs over Springboot, which makes simple to deal with many different aspects of the application like template rendering, configuration management, databases integration, integration patterns, etc...
 
The [index.html](http://localhost:8080) is a very simple page with jQuery embedded to deal with the user request.

There is a Dockerfile available as well to make it easier to start and test the application, to run the application using docker:
```
docker build -t url-shortener .
docker run -it -p 8080:8080 url-shortener
```

To run using Gradle itself in production mode:
```
./gradlew installDist
./build/install/url-shortener/bin/url-shortener
```

To run in development mode:
```
./gradlew bootRun
```

The URL matcher is not considering the `www`, it creates two different alias for the address with and without www, it should be improved on the future.

The endpoints are not using JSON format, whenever a new URL is generated it only returns the short ID itself, and the input params are form-data, for a better interoperability and to be able to exchange more data on the request (like user information, URL statistics, etc) it should use JSON all the way.

The tests are done using SpringRunner, that makes easier to test all the flow. This approach is a good choice for this project since there are no huge and specific logic that needs to be tested isolated (on a unit fashion), the hash function for instance is a third party library very well known and test.
The idea of testing on an integrated fashion (on this small application) guarantees that the coverage (branch coverage) can be the best which automatically makes it safer to put on a automated pipeline, also the tests and code maintenance are easier since the test is very tiny and focused.
The tests assertions are not the best due time constraints, they should not be inspecting the body of the response to check if the error page was displayed, that should be fixed using an ExceptionHandler controller from SpringMVC, making all the exceptions fall at the same bucket and refining them.
