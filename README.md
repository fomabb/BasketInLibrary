# Basket in Library

# RESTful Web-application is written using the following technologies:

- Maven
- Hibernate
- JPA
- PostgresSQL DB
- Spring Boot

### Short description of the project

- Standalone application providing REST API

### Prerequisites:

- Java 19
- PostgresSQL

# Data model

## ER diagram for the data model

![Tables Library.jpg](src%2Fmain%2Fresources%2Fimage%2FTables%20Library.jpg)

## RESTful API

**1. API Description of general methods for User**

 METHOD | PATH           | DESCRIPTION                                          
--------|----------------|------------------------------------------------------
 POST    | /api/user      | create a new user with a personal shopping cart                                      
 GET    | /api/user/{id} | get user by id                                     
 GET    | /api/user      | get all user                                      

___Request body for method create new user___
```json
{
"login":"Ric",
"email": "Ricbb1221@gmail.com",
"password": "java12-rs"
}
```

**2. API Description of general methods for Book**

 METHOD | PATH                 | DESCRIPTION            
--------|----------------------|------------------------
 POST   | /api                 | create new books    
 PUT   | /api                 | updating the number of books in stock     
 GET   | /api/{id}            | get book by id     
 GET   | /api                 | get all books
 GET   | /api?title={title}   | get all books by title
 GET   | /api?genre={genre}   | get all books by genre
 GET   | /api?author={author} | get all books by author

___Request body for method POST___

```json
[
  {
    "title": "Private investigation",
    "author": "Daniel Ribakoff",
    "genre": "Detective",
    "status": "ACTIVE",
    "publisher": "Nik",
    "count": 7
  },
  {
    "title": "WarCraft",
    "author": "Kristi Golden",
    "genre": "Fantasy",
    "status": "ACTIVE",
    "publisher": "Miki",
    "count": 3
  }
]

```

___Request body for method PUT___

```json
{
  "count": 12
}

```

**3. API Description of general methods for Cart**

 METHOD | PATH                                      | DESCRIPTION         
--------|-------------------------------------------|---------------------
 PUT    | /api/cart/cartId/{cartId}/bookId/{bookId} | adding a book to the user's cart from the book warehouse 
 DELETE    | /api/cart/cartId/{cartId}/bookId/{bookId} | returning  a book from a user's shopping cart to a book warehouse 
 GET    | api/cart/{id}                              | get cart by id  
 GET    | api/cart                                  | get all carts users 
 GET    | /api/cart/user/{login}                    | get cart user's by login 

___Response for cart___

````json
{
    "id": 1,
    "dateTime": "25-03-2024 14:51",
    "putDateTime": "26-03-2024 01:52",
    "returnDateTime": "25-03-2024 22:26",
    "books": [
        {
            "id": 5,
            "title": "WarCraft",
            "author": "Kristi Golden",
            "genre": "Fantasy",
            "status": "ACTIVE",
            "publisher": "Miki",
            "count": 1
        },
        {
            "id": 6,
            "title": "Private investigation",
            "author": "Daniel Ribakoff",
            "genre": "Detective",
            "status": "ACTIVE",
            "publisher": "Nik",
            "count": 1
        }
    ],
    "user": {
        "id": 1,
        "login": "Ric",
        "email": "Ricbb1221@gmail.com",
        "password": "202cb962ac59075b964b07152d234b70"
    }
}
````

### My application requests in Postman

[![Run in Postman](https://run.pstmn.io/button.svg)](https://app.getpostman.com/run-collection/d9af219fea3fe665c736?action=collection%2Fimport)
  
