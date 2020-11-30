# Transaction Statistics

The main use case for our API is to calculate realtime statistic from the last 60 seconds. There will be two APIs, one of them is
called every time a transaction is made. It is also the sole input of this rest API. The other one returns the statistic based of the transactions of the last 60 seconds.

Development mindset: 
- The API is thread-safe with concurrent requests.
- The solution works without a database (this also applies to in-memory databases).
- The service does not store all transactions in memory for all time. Transactions not necessary for correct calculation is discarded.

## Specs
#### POST /transactions
This endpoint is called to create a new transaction.

Body:
    
    {
      "amount": "12.3343",
      "timestamp": "2018-07-17T09:59:51.312Z"
    }

Where:

- **amount** – transaction amount; a string of arbitrary length that is parsable as a BigDecimal
- **timestamp** – transaction time in the ISO 8601 format `YYYY-MM-DDThh:mm:ss.sssZ` in the UTC timezone (this is not the current timestamp)

Returns:

    201 - in case of success
    204 - if transaction is older than 60 seconds
    400 – if the JSON is invalid
    422 – if any of the fields are not parsable or the transaction date is in the
    future
    
    
#### GET /statistics
This endpoint returns the statistics computed on the transactions within the last 60 seconds.

Returns:

    {
      "sum": "1000.00",
      "avg": "100.53",
      "max": "200000.49",
      "min": "50.23",
      "count": 10
    }
    
Where:

- **sum** – a BigDecimal specifying the total sum of transaction value in the last 60 seconds
- **avg** – a BigDecimal specifying the average amount of transaction value in the last 60 seconds
- **max** – a BigDecimal specifying single highest transaction value in the last 60 seconds
- **min** – a BigDecimal specifying single lowest transaction value in the last 60 seconds
- **count** – a long specifying the total number of transactions that happened in the last 60 seconds
 
All BigDecimal values always contain exactly two decimal places and use
`HALF_ROUND_UP` rounding. eg: 10.345 is returned as 10.35, 10.8 is returned as
10.80.

#### DELETE /transactions
This endpoint causes all existing transactions to be deleted.
The endpoint should accept an empty request body and return a 204 status code.


## Project Hierarchy

The following files and folder are part of the project:

- README.md : current file
- pom.xml : Maven project main file
- src/main/java :  Source files of the code.
- src/main/resources :  Sample input json file
- src/test/java :  Source files for the JUNIT5 unit tests
- src/test/resources :  input json files used by the unit tests

#### Prerequisites

You will need maven version 3.6.3+ and jdk11 to be able to compile and run this application.

#### Installing

After cloning the project, open a commandline at the main folder that contains project pom.xml file and build the application by using following commands:


```
mvn clean
mvn compile
mvn integration-test
mvn package
```

#### Executing the application

The execution of the application requires an input file that can be passed as argument of the execution.

```
java -jar target/coding-challenge-1.0.3.jar
```



