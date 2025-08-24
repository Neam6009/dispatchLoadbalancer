Dispatch Load Balancer
This Spring Boot application provides a backend service to optimize the allocation of delivery orders to a fleet of vehicles. It uses the Haversine formula to calculate distances and assigns orders based on vehicle capacity, order priority, and travel distance minimization.

## Requirements
To build and run this application locally, you will need:

Java Development Kit (JDK) 17 or higher

Apache Maven

PostgreSQL database server

## Setup
Follow these steps to get the application running on your local machine.

1. Clone the Repository
```
   git clone https://github.com/Neam6009/dispatchLoadbalancer.git
```
```
cd dispatch-load-balancer
```

2. Configure the Database
   Before running the application, you need to set up your PostgreSQL database and configure the connection details.

Create a new database in PostgreSQL. For this example, we'll name it loadBalancer.

Open the src/main/resources/application.properties file.

Update the file with your PostgreSQL server details.

application.properties

### Application Name
spring.application.name=dispatchLoadBalancer

## PostgreSQL Database Configuration
spring.datasource.url=jdbc:postgresql://localhost:5432/loadBalancer
spring.datasource.username=postgres
spring.datasource.password=your_password # <-- Change this to your actual password
spring.datasource.driver-class-name=org.postgresql.Driver

## JPA/Hibernate Configurations
### 'update' will update the schema if it changes, 'create' will drop and recreate it.
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

## Run the Application
1. Build the Project
   Navigate to the project's root directory and use Maven to build the application. This will also download all the necessary dependencies.
```
./mvnw clean install
```

2. Run the Application
   Once the build is successful, you can start the application with the following command:

```
./mvnw spring-boot:run
```

The application will start on http://localhost:8080.

## Tests
To run the complete suite of unit and integration tests, execute the following Maven command from the project root:
```
./mvnw test
```

This command will compile the test classes, run all tests, and provide a summary report.

# API Endpoints
The application exposes the following REST endpoints. You can use tools like Postman or curl to interact with them.

1. Input Delivery Orders
   Accepts a list of delivery orders and saves them to the database.

Endpoint: POST /api/dispatch/orders

Method: POST

Request Body:

```json
{
"orders": [
{
"orderId": "ORD001",
"latitude": 12.9716,
"longitude": 77.5946,
"address": "MG Road, Bangalore, Karnataka, India",
"packageWeight": 10,
"priority": "HIGH"
},
{
"orderId": "ORD002",
"latitude": 13.0827,
"longitude": 80.2707,
"address": "Anna Salai, Chennai, Tamil Nadu, India",
"packageWeight": 20,
"priority": "MEDIUM"
}
]
}
```


Success Response (200 OK):
```json
{
"message": "Delivery orders accepted.",
"status": "success"
}
```


2. Input Fleet Details
   Accepts a list of vehicles and saves them to the database.

Endpoint: POST /api/dispatch/vehicles

Method: POST

Request Body:
```json
{
"vehicles": [
{
"vehicleId": "VEH001",
"capacity": 100,
"currentLatitude": 12.9716,
"currentLongitude": 77.6413,
"currentAddress": "Indiranagar, Bangalore, Karnataka, India"
},
{
"vehicleId": "VEH002",
"capacity": 150,
"currentLatitude": 13.0674,
"currentLongitude": 80.2376,
"currentAddress": "T Nagar, Chennai, Tamil Nadu, India"
}
]
}
```


Success Response (200 OK):
```json
{
"message": "Vehicle details accepted.",
"status": "success"
}
```


3. Retrieve Dispatch Plan
   Calculates and returns the optimized dispatch plan based on all orders and vehicles currently in the system.

Endpoint: GET /api/dispatch/plan

Method: GET

Success Response (200 OK):

```json
{
"dispatchPlan": [
{
"vehicleId": "VEH001",
"totalLoad": 10,
"totalDistance": "5 km",
"assignedOrders": [
{
"orderId": "ORD001",
"latitude": 12.9716,
"longitude": 77.5946,
"address": "MG Road, Bangalore, Karnataka, India",
"packageWeight": 10,
"priority": "HIGH"
}
]
},
{
"vehicleId": "VEH002",
"totalLoad": 20,
"totalDistance": "4 km",
"assignedOrders": [
{
"orderId": "ORD002",
"latitude": 13.0827,
"longitude": 80.2707,
"address": "Anna Salai, Chennai, Tamil Nadu, India",
"packageWeight": 20,
"priority": "MEDIUM"
}
]
}
],
"unassignedOrders": []
}
```

