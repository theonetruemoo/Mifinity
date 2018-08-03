# Mifinity Test Project

Requirements:
Create a basic Java/JEE web application that is composed of the following:

1)Create a login screen that should include a new user creation function for unique
users (username and password should be enough)

2)In one page a form that takes a credit card number, a name and an expiry date (in
the format YY/MM) that on submit will store that card, if the card is new, or update
the card expiry date if the card is already there. The application should confirm the
entered card data is valid before storing or updating the card.

3)On another page, have a text field that can accept a credit card number and a button
to search. When search is clicked then the system should return the credit card data.
This data should be displayed in a table. (if the user types in the search text part of
the credit card number the system might show more than one credit card.)

4)Make sure that the card created is visible only to the user who created it or to user
with administrator privileges (admin user credentials can be defaulted to some fixed
value)


### Getting Started

Download the project
set the active profile to sql 
(there is a second h2 active profile so if you would prefer to use the in memory db then you can, just add the admin user otherwise you will not be able to fully test the functionality)
change the settings in the application-sql.properties:
spring.datasource.url=jdbc:mysql://[Database url]:{port}/{schema}

Please ensure that the schema exists.

Clean and build the project using maven

Start the project (the project uses spring boot)

Go to the DB and run the following to create the admin user(all other users that are created are general 'users')

INSERT INTO USERS VALUES(1,'d033e22ae348aeb5660fc2140aec35850c4da997','0','Admin');

Once the server has started up please go to http://localhost:8080/login.xhtml

Admin Login details:

username: admin

password: admin



## Tests

In a real world project I would have put tests for all the services and repositories, how ever since this is just a "throw away project" 
I have only done the User Repository and the User Service to demonstrate knowledge of unit tests and integration tests.

## Things to note

The Search and create card are put on a single screen so the reviewer doesn't have to bounce around seperate screens to test the functionality.



