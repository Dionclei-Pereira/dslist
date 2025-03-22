![Java](https://img.shields.io/badge/Java-21-blue)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.4-green)
![Spring Data JPA](https://img.shields.io/badge/Spring%20Data%20JPA-3.4-green)
![Spring Security](https://img.shields.io/badge/Spring%20Security-6.4.1-blue)

# Spring Boot Game List

## 📖 Description

This project implements a simple backend. The main objective is create an API that manages a list of games to a review website or games info.

## 🚀 **Technologies**

The main technologies used in this project are:

- ✅ Java 21 
- ✅ Spring Boot  
- ✅ Hibernate  
- ✅ H2 DataBase
- ✅ Spring Security
- ✅ JWT - HMAC

## 🎯 **Features**
- ✅ Games  
- ✅ Game Lists
- ✅ Reorder games  
- ✅ JWT Tokens
- ✅ Paged result
  
## ⚙ Prerequisites

Install these programs:

- **Java 21**
- **IDE** (IntelliJ IDEA, Eclipse, VSCode.)
- **Maven**
- **Postman** (or similiar.)

## ⚡ Steps to Run the Project

### 1. Clone the repository

Clone the project to your local environment:

```bash
git clone https://github.com/Dionclei-Pereira/dslist.git
cd dslist
```

### 2. Run the Project

To run the project, you can use the your IDE or Maven CLI:

### 4. Testing the API

The API is configured to allow login and generate a JWT token. You can use **Postman** to test the routes.

- **POST** `/auth/login`: Send an `email` and `password` to receive a JWT token.
- **GET** `/games`: This route is protected and requires a valid JWT token in the Authorization header.

Example request for login:

POST /auth/login
```json
{
  "email": "email@gmail.com",
  "password": "password"
}
```

If the login is successful, a JWT token will be returned.

Example request to access a protected route:

- **GET** `/games` <br>
Authorization: _your-jwt-token_

## 📑 API Endpoints

| Method | Endpoint | Description | Request Body | Response |
|--------|----------|-------------|--------------|----------|
| `POST` | `/auth/login` | Authenticates a user and returns a JWT token | `{ "email": "email@gmail", "password": "passw" }` | `{ "token": "eyJhbGciOiJIUzUxMiJ9..." }` |
| `POST` | `/auth/register` | Authenticates a new user | `{"email": "email@gmail.com","phone": 999999999,"name": "username","password": "password"}` | `200 OK`<br>`400 Bad Request` |
| `GET`  | `/games` | Retrieves all games(paged ex. /games?page=2) | - | `200 OK` |
| `GET`  | `/games/{id}` | Retrieves a specific game by ID | - | `200 OK`<br>`404 Not Found`|
| `POST (ADMIN)` | `/games` | Creates a new game | `{"title": "The Witcher 3: Wild Hunt", "year": 2015, "genre": "RPG", "platforms": "PC, PS4, Xbox One", "score": 11, "imgUrl": "https://example.com/witcher3.jpg","shortDescription": "An open-world RPG.", "longDescription": "The Witcher 3: Wild Hunt is a game"}` | `201 Created`<br>`400 Bad Request`<br>`403 Forbidden`|
| `PUT (ADMIN)`  | `/games/{id}` | Updates a game by ID | `{"title": "The Witcher 4"}` | `200 OK`<br>`404 Not Found`<br>`403 Forbiden`<br>`400 Bad Request` |
| `DELETE (ADMIN)` | `/games/{id}` | Deletes a game by ID | - | `204 No Content`<br>`403 Forbidden`<br>`404 Not Found`<br>`400 Bad Request` |
| `GET`  | `/lists` | Retrieves all lists | - | `200 OK` |
| `GET`  | `/lists/{id}/games` | Retrieves a specific list of games by the list's ID(paged ex. /lists/1/games?page=2) | - | `200 OK`<br>`400 Bad Request`|
| `POST`  | `/lists/{id}/replacement` | Move a game within the game list | `{ "sourceIndex": "1", "destinationIndex": "4" }` | `200 OK`<br>`400 Bad Request`|
| `POST`  | `/lists/{listId}/games/add-game/{gameId}` | Add a game to a list | - | `200 OK`<br>`404 Not Found`|
| `POST (ADMIN)`  | `/lists` | Creates a new game list | `{"name": "Adventure"}` | `200 OK`<br>`400 Bad Request`<br>`403 Forbiden`|
| `PUT (ADMIN)`  | `/lists` | Updates a game list | `{"name": "AAA"}` | `200 OK`<br>`400 Bad Request`<br>`403 Forbiden`|
| `DELETE (ADMIN)`  | `/lists/{id}` | Deletes a list by ID | - | `204 No Content`<br>`403 Forbidden`<br>`404 Not Found`<br>`400 Bad Request`|

## 📜Author

**Dionclei de Souza Pereira**

[Linkedin](https://www.linkedin.com/in/dionclei-de-souza-pereira-07287726b/)

⭐️ If you like this project, give it a star!  
