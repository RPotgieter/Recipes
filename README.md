# Recipe Api

### Summary

This is part of a technical assessment, where the idea is to create a simple api that
does simple crud operations to persist user entered recipes via the api.

### Guide

#### Running the API

* A running instance of MongoDb is needed to run the api.
* Make sure to point the api properties to the correct mongo ip (application.properties), by default it will be set to 
the docker container mapping from docker-compose.yml

To get the whole stack up and running, run:
```
docker-compose up -d
```
Getting logs:
```
docker-compose logs api -f
```

It takes a few seconds for Gradle to do its thing.

Then visit http://localhost:8080/api/v1/recipe (authentication will be prompted, see Authentication section) </br>
Or, visit http://localhost:8080/api/v1/ to explore via HAL.

The optional Paging, size and sorting is available as well. </br>
To use the paging for example, simply pass ?page=2 as a query parameter.

If you wish to run the api directly, run (requires mongo):
```gradle
./gradlew bootrun
```

This will also start up the api on port 8080, making the same url apply here.

#### Authentication
The application has Basic authentication applied, with the following credentials:
```
username: admin
password: password
```
This means if you are viewing the data in a browser, you will get a popup that asks for credentials. </br>
To call the api, you will need to provide the Authorization header accordingly.

#### Api documentation
Please see Postman collection added in the project root.

### Stack choices
* Spring boot - Very easy to use with a ton of config already done out of the box
  * Spring Data Rest - This will generate api's for you based off what you specify in a pojo. I've opted for this as it's
  the easiest and quickest way to get a fully functional CRUD on REST. This also sets up all the config for the 
  data layer, so you just need to supply the connection url.
* MongoDB - I've chosen Mongo simply because I wanted to learn more on how to integrate it, as I haven't really 
worked with it before.