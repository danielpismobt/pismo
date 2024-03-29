# Pismo Back Challenge

Repository containing the solution for Pismo backend role challenge.


## Minimum requirements

- Java 21 (you can skip if you are running using Docker only)
- Docker

## Usage

You should run the project using Docker along with Docker Compose. Run the following commands in the project root directory:

### Build

```sh
docker-compose build
```

### Run

```sh
docker-compose up -d
```

The application will run using port 8080 by default. You can access swagger interface through `http://localhost:8080/swagger`

## Optional configuration

You are able customize some fields editing the `docker-compose.yml` file. Some values are editable, such as:

- SPRING_DATASOURCE_URL
- SPRING_DATASOURCE_USERNAME
- SPRING_DATASOURCE_PASSWORD
- MYSQL_ROOT_PASSWORD
- MYSQL_DATABASE
