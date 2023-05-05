# User Account Management Services

This project is a collection of Spring Boot microservices that provide user account management functionality. The services are designed to work together to provide a seamless user experience and ensure the security and scalability of the system.

## Requirements

To run this project, you will need:

- Java 11 or higher , the project was fully tested on Jav 17
- Maven 3.6 or higher
- Docker and Docker Compose (optional, for running the services in containers)

## Running the Services

To run the services locally, follow these steps:

1. Clone the repository: `git clone https://github.com/rogerndutiye/user-account-management.git`
2. CD(go) to each and every service you want start and Build the services: `mvn clean package`
3. Run the services: `mvn spring-boot:run` (alternatively, you can run the services in Docker containers by running `docker-compose up --build` in the project root directory)

## Service Endpoints

The following endpoints are provided by the services:

- user-auth-service Service:
    - `/swagger-ui/index.html`: API Documentation Endpoint
    - `/api/auth/login`: First step for User login , this generate the OTP 
    - `/api/auth/otp`: User login with the above generated OTP
    - `/api/users/reset-password`: First step for Reset user password, this generate reset password OTP
    - `/api/users/confirm-reset-password`: Reset user password using the above generate reset password OTP
    - `/api/auth/register`: Create a new user account
    - `/api/users/{userId}/profile`: use PUT to complete user profile information
    - `/api/users/{userId}/profile`: Get user profile information
    - `/api/users/{id}/verify-account`: Submit account verification information
    - `/api/users/verify-account/callback`: call back url that listening to the account verification decision
  
- File server Service:
    - `/api/files/upload`: Submit Images/Documents (user profile photo and account verification document) 

- Email Service:
    - use Kafka by listening the broadcast email notification then Send notification to user

- kafka:
    - used as a data streaming between the above services

## Architecture

The services are designed using a microservices architecture, with each service responsible for a specific set of functionality. The services communicate with each other using RESTful APIs and Kafka, and data is stored in a shared data storage layer. The system is designed for high availability and scalability, with each service deployed independently and able to handle large volumes of traffic.

## Testing

Unit tests and integration tests are provided for each service, and can be run using `mvn test`.

## Security

The services are designed with security in mind, using industry-standard security practices such as password hashing, and multi-factor authentication. Passwords are enforced to meet strength requirements, and users can opt to use multi-factor authentication for increased security.

## Contact

If you have any questions or issues, please contact us at rogerndutiye@gmail.com
