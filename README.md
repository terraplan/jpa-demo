# JPA Demo
Demo project for JPA topics

## Things to add

- Ordered lists
- Lazy loading

## Setup and Running

- create an empty postgresql database, called "jpa_demo" (hibernate is configured to automatically create and update the schema)
- adapt creddentials in `application.properties`
- to start run `gradlew bootRun`

## Usage

You can submit requests to the API using Postman, e.g.

Places, Customers and Comments have to be created manually in the DB to have some data to start with. Please take care 
to not reuse IDs. E.g. use 100* for Places, 200* for Customers, etc. 

**Create an order**: POST localhost:8080/orders

with the following JSON:
```JSON
{
  "name":"XYZ",
  "orderItems": [
    {
      "name":"xyz"
    }
  ],
  "placeId": "1001",
  "customers": ["3001"]
}
```

**Update an order**: PUT localhost:8080/orders/{id}

**Get an order**: GET localhost:8080/orders/{id}

**Delete an order**: DELETE localhost:8080/orders/{id}
