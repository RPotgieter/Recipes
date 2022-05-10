# Recipe Api

### Summary

This is part of a technical assessment, where the idea is to create a simple api that
does simple crud operations to persist user entered recipes via the api.

### Guide

#### Running the API

* A running instance of MongoDb is needed to run the api.

To get the whole stack up and running, run:
```
docker-compose up -d
```

Then visit http://localhost:8080/api/v1/recipe (authentication will be prompted, see Authentication section)
Or, visit http://localhost:8080/api/v1/ to explore via HAL.

The optional Paging, size and sorting is available as well.
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
This means if you are viewing the data in a browser, you will get a popup that asks for credentials.
To call the api, you will need to provide the Authorization header accordingly.

#### Api documentation
Please see Postman collection added in the project root.