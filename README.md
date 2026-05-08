# Fly Away Travel API

Lab 07 - CS2031 DBP. API REST para crear vuelos, registrar usuarios, login JWT, buscar vuelos y reservar.

## Ejecutar

```bash
mvn spring-boot:run
```

Base URL:

```text
http://localhost:8080
```

## Endpoints principales

- POST `/flights/create` sin token
- POST `/users/register` sin token
- POST `/auth/login` sin token
- GET `/flights/search` con token
- POST `/flights/book` con token
- GET `/flight/book/{id}` con token
- DELETE `/cleanup` sin token

## Token en Postman

Authorization > Bearer Token > pegar el token del login.
