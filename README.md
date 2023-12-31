# Wallet-Service

Wallet-Service is a Java-based console application that provides wallet management functionality for players.
It allows me to perform transactions, view my current balance, and check my transaction history. 
The application also supports player authorization, audit logging, and admin services.

## Table of Contents
- [Prerequisites](#prerequisites)
- [Building the Project](#building-the-project)
- [Running the Project](#running-the-project)


## About

This project is developed under the mentorship of [Y_lab Development](https://ylab.io/), a digital production company 
specializing in IT project implementation and business process automation for companies.

## Features

- **Player Authorization**: I can log in to my personal wallet with my credentials.
- **Perform Transactions**: I can perform debit and credit transactions on my wallet.
- **View Transaction History**: I can check my transaction history to track my financial activities.
- **Check Current Balance**: I can view my current wallet balance.
- **Audit Logging**: All transactions and my actions are logged for audit purposes.
- **Admin Services**: Admins can manage my data and access audit logs.

## Technology Stack

- **Java**: The application is written in Java.
- **Console Interface**: It uses a console-based user interface for interaction.
- **Database**: Uses a relational database to store player, transaction, admin, and audit log data.
- **Liquibase**: Utilized for managing database migrations.
- **JUnit and Mockito**: Used for unit testing.
- **Service-Oriented Architecture**: The application is designed with service components that handle different aspects
  of the wallet system.

## Prerequisites

Before building and running the project, make sure you have the following prerequisites installed on your system:
- [Java Development Kit (JDK)](https://www.oracle.com/java/technologies/javase-downloads.html) - The project is written
- in Java, so you need to have the JDK installed.
- [Maven](https://maven.apache.org/download.cgi) - Maven is used for managing project dependencies and building the project.

## Building the Project
To build the project, follow these steps:

1. Clone the repository to your local machine: [Repository](https://github.com/den4os/Wallet-Service)
2. Navigate to the project's root directory: `cd wallet-service`
3. Update the `application.properties` file with your database credentials.
4. Build the project using Maven: `mvn clean package`
5. The above command will compile the project and create an executable JAR file.

## Running the Project

Once the project is built, you can run it using the following command: java -jar target/wallet-service.jar
This will start the Wallet-Service application, and you can interact with it through the console-based interface.

Feel free to explore the application's features and functionalities.

If you encounter any issues or have questions, please refer to the [Contributing](#contributing) section for
information on how to get help or contribute to the project.

## Usage

1. When the application starts, I will be prompted to log in.
2. Use the console interface to navigate the available options.
3. Perform transactions, check your balance, or view transaction history as a player.
4. Admins can manage players and access audit logs.

## Contributing

I welcome contributions from the community. If you'd like to contribute to the project, please follow these steps:

1. Fork the repository.
2. Create a new branch for your feature or bug fix.
3. Make your changes and ensure all tests pass.
4. Submit a pull request with a detailed description of your changes.

## License

This project is licensed under the [Apache License 2.0](LICENSE) - see the [LICENSE](LICENSE) file for details.

## Acknowledgments

Special thanks to all contributors and the open-source community for their support and contributions to this project.
