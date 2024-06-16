# Ridango challenge

Made in Java 22.

## Before running
To make our lives easier we preprepared an sqlite DB to query the data we need.
We can to this by running (name must be `schedule.db` as our code depends on it): 
```sh
sqlite schedule.db
```

and then

```sh
mode csv
.import stops.csv stops
.import stop_times.csv stop_times
.import trips.csv trips
.import routes.csv routes
.exit
```

Our program expects a specific data of course. Source files can be found in the project.

## How to run
The program accepts three input parameters:
```sh
java -jar ridango.jar stopId numberOfBuses timeFormat
```

where:
  - `stopId` is the identifier of a specific bus stop in our DB.
  - `numberOfBuses` is the identifier of a specific bus stop in our DB.
  - `timeFormat` either `relative` or `absolute`. Determines the output of times (`12:05` or `5min`).

In order for the project to work you must provide an sqlite database in the same directory as the ran jar.



## Issues
There are some SLF4J errors when running the app. Should be fixable by following the url, but its not critical.

```sh
SLF4J: Failed to load class "org.slf4j.impl.StaticLoggerBinder".
SLF4J: Defaulting to no-operation (NOP) logger implementation
SLF4J: See http://www.slf4j.org/codes.html#StaticLoggerBinder for further details.
```

