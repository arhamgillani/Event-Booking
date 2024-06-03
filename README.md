Event Booking System
This is a Spring Boot application for an Event Booking System. The application allows users to register, login, book tickets, manage events, and view bookings.


Table of Contents
* Getting Started
* Building and Running the Application
* API Documentation
   * User API
   * Booking API
   * Event API
* Technologies Used


Prerequisites
* Java 17
* Maven
* An IDE (e.g., IntelliJ IDEA, Eclipse)
* (Optional) Postman for API testing


Clone the Repository
Git clone https://github.com/arhamgillani/Event-Booking.git


Building and Running the Application

Build the Application
Use the following Maven command to build the application:
mvn clean install
Run the Application
Use the following Maven command to run the application:
mvn spring-boot:run


The application will start running on http://localhost:8080.

API Documentation
User API
Register a User
* URL: /api/users/register
* Method: POST
Request Body:
{
  "username": "string",
  "password": "string",
  "email": "string"
}
Response:
{
  "id": "long",
  "username": "string",
  "password": "string",
  "email": "string"
}


Login a User
* URL: /api/users/login
* Method: POST
* Request Params:
   * username: string
   * password: string
Response:
{
  "message": "Login Successfully",
  "user": {
    "id": "long",
    "username": "string",
    "email": "string"
  },
  "error": false
}




Booking API
Book Tickets
* URL: /api/bookings/bookTickets
* Method: POST
* Request Params:
   * eventId: long
   * userId: long
   * numberOfTickets: int
Response:
{
  "id": "long",
  "eventId": "long",
  "userId": "long",
  "numberOfTickets": "int",
  "bookingDate": "string"
}
Get Bookings by User
* URL: /api/bookings/user/getBookingsByUser
* Method: GET
* Request Params:
   * userId: long
Response:
[
  {
    "id": "long",
    "eventId": "long",
    "userId": "long",
    "numberOfTickets": "int",
    "bookingDate": "string"
  },
  ...
]
Cancel Booking
* URL: /api/bookings/cancelBooking
* Method: DELETE
* Request Params:
   * bookingId: long
* Response: 204 No Content
Event API
Create an Event
* URL: /api/events/createEvent
* Method: POST
Request Body:
{
  "name": "string",
  "description": "string",
  "date": "string",
  "location": "string"
}
Response:
{
  "id": "long",
  "name": "string",
  "description": "string",
  "date": "string",
  "location": "string"
}
Update an Event
* URL: /api/events/updateEvent
* Method: PUT
* Request Params:
   * eventId: long
Request Body:
{
  "name": "string",
  "description": "string",
  "date": "string",
  "location": "string"
}
Response:
{
  "id": "long",
  "name": "string",
  "description": "string",
  "date": "string",
  "location": "string"
}
Delete an Event
* URL: /api/events/deleteEvent
* Method: DELETE
* Request Params:
   * eventId: long
* Response: 204 No Content


Get All Events
* URL: /api/events/getAllEvents
* Method: GET
Response:
[
  {
    "id": "long",
    "name": "string",
    "description": "string",
    "date": "string",
    "location": "string"
  },
  ...
]
Get Event by ID
* URL: /api/events/getEventById
* Method: GET
* Request Params:
   * eventId: long
Response:
{
  "id": "long",
  "name": "string",
  "description": "string",
  "date": "string",
  "location": "string"
}
