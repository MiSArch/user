# User Service

The user service provides the bounded context `User`. It is responsible for managing users by interacting with Keycloak.

## Documentation

Detailed information about the user service can be found in the [documentation](https://misarch.github.io/docs/docs/dev-manuals/services/user).


## Getting started

A development version of the user service can be started using docker compose:

```bash
docker-compose -f docker-compose.dev.yml up --build
```
A GraphiQL interface is available at http://localhost:8080/graphiql to interact with the service.

> [!NOTE]
> Running the service locally through the IDE is neither recommended nor supported.