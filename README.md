# Bakery Task

## 1. API Definition
```sh
POST http://localhost:8080/collect-packs
``` 

Request body includes a list of orders (OrderRequest)
```sh
[
  {
    "productCode": "VS5",
    "total": 10
  },
  {
    "productCode": "MB11",
    "total": 14
  }
]
```
Response includes a list of order responses (OrderResponse)
```sh
[
  {
    "productCode": "VS5",
    "total": 10,
    "packs": [
      {
        "quantity": 2,
        "packSize": 5,
        "packPrice": 8.99
      }
    ],
    "totalPrice": 17.98
  },
  {
    "productCode": "MB11",
    "total": 14,
    "packs": [
      {
        "quantity": 1,
        "packSize": 8,
        "packPrice": 24.95
      },
      {
        "quantity": 3,
        "packSize": 2,
        "packPrice": 9.95
      }
    ],
    "totalPrice": 54.8
  }
]
````

## 2. Model structure
#### Pack 
| Field| Description  |  
|------|--------------|
| size | number of products |
| price | pack price |
#### PackCode 
| Field| Description  |  
|------|--------------|
| code | product code |
| name | product name |
| packs | list of packs |  

## 3. Solution Design
I implemented an Rest API in order to find the minimal number of packs corresponding to each order request.

Here are the major components:  
- BakeryController: handle Rest API
- BakeryService: calculate and minimal number of packs for each order. The detail solution is implemented in BakeryHelper
- BakeryData: mock bakery data   
- RestExceptionHandler: handle invalid request ( not-readable request body, invalid produc code ..)

## 4. Build, Test and Deployment
Build
```sh
mvn clean package
```
Run Unit Test
```sh
mvn clean test
```
Start application
```sh
mvn spring-boot:run
```
To test APi with swagger-ui, open a web browser and navigate to [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html )


#### Note: With the given API Definition, we can also test API by external tool(Postman).    