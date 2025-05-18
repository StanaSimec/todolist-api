# Todo List API

### Solution for the [todo-list-api](https://roadmap.sh/projects/todo-list-api) project from [roadmap.sh](https://roadmap.sh).

## Open project
```
$ git clone https://github.com/StanaSimec/todolist-api.git
$ cd todolistapi
```
## Set up environment variables
- Open src/main/java/resources/application.yml
- Set environment variables:
    - spring.datasource.url
    - spring.datasource.username
    - spring.datasource.passsword
    - jwt.secret
    - jwt.duration

## How to run the app
```
.gradlew/ bootRun
```

## How to register
```
POST api/v1/register
{
    "username": "username",
    "email": "email@mail.com",
    "password": "password"
}
```
## How to login
```
POST api/v1/login
{
    "email": "email@mail.com",
    "password": "password"
}
```
## How to create todo
```
POST api/v1/todos
{
    "title": "Make a coffe",
    "description": "Make a espresso"
}
```
## How to update todo
```
PUT api/v1/todos/1
{
    "id": 1,
    "title": "Make a coffe",
    "description": "Make a espresso"
}
```
## How to delete todo
```
DELETE api/v1/todos/1
```

## Find todo
```
GET api/v1/todos/1
```

## Find all todos
```
GET api/v1/todos?page=1&limit=10
```

## Tech Stack
- Java
- Gradle
- Spring Boot Starter Web
- Spring Boot Starter JDBC
- Spring Boot Starter Security
- Spring Boot Starter Validation
- Auth0
- PostgreSQL