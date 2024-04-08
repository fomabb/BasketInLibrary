# Basket in Library

# RESTful Web-application is written using the following technologies:

- Maven
- Hibernate
- JPA
- PostgresSQL DB
- Spring Boot
- Spring security

### Short description of the project

- Standalone application providing REST API

### Prerequisites:

- Java 19
- PostgresSQL

# Data model

## ER diagram for the data model

![diagram_db.png](src%2Fmain%2Fresources%2Fimage%2Fdiagram_db.png)

## RESTful API

**1. API Description of general methods for User**

 METHOD | PATH                                               | DESCRIPTION                                     
--------|----------------------------------------------------|-------------------------------------------------
 POST   | /auth/registration                                 | registration user with ROLE_USER and with create a personal shopping cart 
 POST   | /auth                                  | authentication user  
 POST   | /api/user/faq/question/{categoryId}                | ask a question by category by id                
 PUT   | /api/user/faq/update/faqId/{faqId}                 | update text question                
 GET    | /api/user/{id}                                     | get user by id                                  
 GET    | /api/admin/allUsers                                 | get all user for admin                                   
 DELETE | /api/user/faq/categoryId/{categoryId}/faqId/{faqId} | remove faq from description category  

___Request body for method registration new user with your basket___

```json
{
  "login": "Ric",
  "email": "ricbb@gmail.com",
  "password": "java12-rs",
  "confirmPassword" : "java12-rs"
}
```

___Request body authentication user___

```json
{
  "username" : "Ric",
  "password" : "java12-rs"
}
```

___Response jwtToken___

```json
{
    "token": "eyJhbGciOiJIUzI1NiJ9.eyJyb2xlcyI6WyJST0xFX1VTRVIiXSwic3ViIjoiUmljIiwiaWF0IjoxNz
              EyMzQwMDk3LCJleHAiOjE3MTIzNDA2OTd9.apv25A_5vuHU3xooX-32w6-t2UnLorkx0zXz-LouRmw"
}
```

___Filtering authentication by {/api/user/(**)} and {/api/admin/(**)}___

___Response user with basket___

```json
[
  {
    "id": 1,
    "dateTime": "25-03-2024 14:51",
    "books": [],
    "user": {
      "id": 1,
      "login": "Ric",
      "email": "Ric@gmail.com",
      "password": "$2a$10$iSOYg/4dUsKaYn.a5V/THO11XkQnpcKsG0jyGE/i4U3xDQarcakDW",
      "roles": [
        {
          "id": 1,
          "name": "ROLE_USER"
        }
      ]
    }
  }
]
```

___Request body for method ask a question___

```json
{
  "question": "Which book from this genre is the best rated?"
}
```

___Response category book___

```json
{
  "data": [
    {
      "id": 4,
      "title": "Rune",
      "author": "James",
      "genre": "Fantasy",
      "status": "ACTIVE",
      "publisher": "Miki",
      "count": 1
    },
    {
      "id": 5,
      "title": "WarCraft",
      "author": "Kristi Golden",
      "genre": "Fantasy",
      "status": "ACTIVE",
      "publisher": "Miki",
      "count": 1
    }
  ],
  "descriptionData": [
    {
      "id": 3,
      "title": "Fantasy category",
      "category": "Fantasy",
      "description": "This fantasy genre",
      "faq": [
        {
          "id": 1,
          "dateQuestionCreate": "04-04-2024 10:02",
          "question": "Which book from this genre is the best rated?",
          "answer": "null",
          "dateAnswerCreate": "null"
        }
      ]
    }
  ],
  "paginationInfo": {
    "amount": 2
  }
}
```

**2. API Description of general methods for Book**

 METHOD | PATH                                       | DESCRIPTION                                   
--------|--------------------------------------------|-----------------------------------------------
 POST   | /api/book                                  | create new books                              
 PUT    | /api/book/{bookId}                         | updating the count of books in stock          
 GET    | /api/book/{id}                             | get book by id                                
 GET    | /api/book?page={page}&size={size}          | get all books                                 
 GET    | /api/book/search?text={title/genre/author} | full-text search for books from the warehouse 

___Request body for method POST create new books___

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

___Request body for method PUT updating the count of books in stock___

```json
{
  "count": 12
}

```

___Response body for method GET search /api/search?text=war kristi golden___

```json
[
  {
    "id": 5,
    "title": "WarCraft",
    "author": "Kristi Golden",
    "genre": "Fantasy",
    "status": "INACTIVE",
    "publisher": "Miki",
    "count": 0
  }
]
```

**3. API Description of general methods for Cart**

 METHOD | PATH                                      | DESCRIPTION                                                       
--------|-------------------------------------------|-------------------------------------------------------------------
 PUT    | /api/user/cartId/{cartId}/bookId/{bookId} | adding a book to the user's cart from the book warehouse          
 DELETE | /api/user/cartId/{cartId}/bookId/{bookId} | returning  a book from a user's shopping cart to a book warehouse 
 GET    | /api/cart/{id}                            | get cart by id                                                    
 GET    | /api/cart                                 | get all carts users                                               
 GET    | /api/admin/cartByUser?username={username} | get cart user's by login                                          
 GET    | /api/cart/allCarts/{cartId}               | show aa orders by Id cart                                         

___Response for cart___

````json
{
  "id": 1,
  "dateTime": "25-03-2024 14:51",
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
    "email": "ricbb@gmail.com",
    "password": "$2a$10$iSOYg/4dUsKaYn.a5V/THO11XkQnpcKsG0jyGE/i4U3xDQarcakDW"
  }
}
````

___Response show all orders___

````json
[
  {
    "orderNumber": 1323,
    "creationTime": "26-03-2024 15:59",
    "book": {
      "id": 5,
      "title": "WarCraft",
      "author": "Kristi Golden",
      "genre": "Fantasy",
      "status": "ACTIVE",
      "publisher": "Miki",
      "count": 1
    }
  },
  {
    "orderNumber": 1324,
    "creationTime": "26-03-2024 15:59",
    "book": {
      "id": 6,
      "title": "Private investigation",
      "author": "Daniel Ribakoff",
      "genre": "Detective",
      "status": "ACTIVE",
      "publisher": "Nik",
      "count": 1
    }
  }
]
````

**4. API Description of general methods for Tree**

 METHOD | PATH                                                                        | DESCRIPTION                                                         
--------|-----------------------------------------------------------------------------|---------------------------------------------------------------------
 POST   | /api/admin/createCategory                                                         | create new category in tree                                         
 PUT    | /api/admin/addChildrenId/{childId}?parentNode={parentId}                          | add children category in parent                                     
 PUT    | /api/admin/addBookId/{bookId}/categoryId/{categoryId}                             | add book in category for tree                                       
 GET    | /api/book/category/{categoryId}?parent={true/false}&page={page}&size={size} | display all books for the category by id {true}parent, {false}child 

___Request body for create categories method___

```json
[
  {
    "category": "Genres"
  },
  {
    "category": "Action"
  },
  {
    "category": "Fantasy"
  },
  {
    "category": "Detective"
  }
]
```

___Response all categories with books with the implementation of infinite tree deepening___

```json
{
  "data": [
    {
      "id": 1,
      "category": "Genres",
      "children": [
        {
          "id": 2,
          "category": "Action",
          "children": [],
          "books": [
            {
              "id": 1,
              "title": "War and Peace",
              "author": "Tolstoy",
              "genre": "Action",
              "status": "Active",
              "publisher": "Mike",
              "count": 1
            },
            {
              "id": 2,
              "title": "Goal",
              "author": "Eliyagu",
              "genre": "Action",
              "status": "ACTIVE",
              "publisher": "Rik",
              "count": 1
            }
          ]
        },
        {
          "id": 3,
          "category": "Fantasy",
          "children": [],
          "books": [
            {
              "id": 4,
              "title": "Rune",
              "author": "James",
              "genre": "Fantasy",
              "status": "ACTIVE",
              "publisher": "Miki",
              "count": 1
            },
            {
              "id": 5,
              "title": "WarCraft",
              "author": "Kristi Golden",
              "genre": "Fantasy",
              "status": "ACTIVE",
              "publisher": "Miki",
              "count": 1
            }
          ]
        },
        {
          "id": 4,
          "category": "Detective",
          "children": [],
          "books": [
            {
              "id": 6,
              "title": "Private investigation",
              "author": "Daniel Ribakoff",
              "genre": "Detective",
              "status": "ACTIVE",
              "publisher": "Nik",
              "count": 1
            },
            {
              "id": 3,
              "title": "The girl with the dragon tattoo",
              "author": "Stig Larsson",
              "genre": "Detective",
              "status": "ACTIVE",
              "publisher": "Nik",
              "count": 1
            }
          ]
        }
      ],
      "books": []
    }
  ],
  "descriptionData": [
    {
      "id": 1,
      "title": "Genre category",
      "category": "The best books",
      "description": "This the best books for all users",
      "faq": [
      ]
    }
  ],
  "paginationInfo": {
    "amount": 8
  }
}
```

**5. API Description of general methods for Admin**

 METHOD | PATH                                       | DESCRIPTION                       
--------|--------------------------------------------|-----------------------------------
 PUT    | /api/admin/{faqId}                         | answer the question on the faq id and update text 
 DELETE | /api/admin/categoryId/{categoryId}/faqId/{faqId} | remove faq from description category 
 POST | /api/admin/description | create description by id category 

___Request body for description___

```json
{
  "id": 3,
  "title": "Fantasy category",
  "category": "Fantasy",
  "description": "This fantasy genre"
}
```

___Request body for answer___

```json
{
  "answer": "Considered one of the best publications, this is a series of WarCraft books by author Kristi Golden."
}
```

___Response answer___

```json
{
  "data": [
    {
      "id": 4,
      "title": "Rune",
      "author": "James",
      "genre": "Fantasy",
      "status": "ACTIVE",
      "publisher": "Miki",
      "count": 1
    },
    {
      "id": 5,
      "title": "WarCraft",
      "author": "Kristi Golden",
      "genre": "Fantasy",
      "status": "ACTIVE",
      "publisher": "Miki",
      "count": 1
    }
  ],
  "descriptionData": [
    {
      "id": 3,
      "title": "Fantasy category",
      "category": "Fantasy",
      "description": "This fantasy genre",
      "faq": [
        {
          "id": 1,
          "question": "Which book from this genre is the best rated?",
          "dateQuestionCreate": "04-04-2024 10:02",
          "answer": "Considered one of the best publications, this is a series of WarCraft books by author Kristi Golden.",
          "dateAnswerCreate": "04-04-2024 10:03"
        }
      ]
    }
  ],
  "paginationInfo": {
    "amount": 2
  }
}
```

### My application requests in Postman

[![Run in Postman](https://run.pstmn.io/button.svg)](https://app.getpostman.com/run-collection/d9af219fea3fe665c736?action=collection%2Fimport)
  
