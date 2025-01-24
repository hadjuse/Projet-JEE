Here is the translation of the README file into English:

# Configuration of the MySQL Database with Docker

This document provides instructions for setting up a MySQL database using Docker and integrating connection information into a Jakarta EE application.

## Prerequisites

1. **Docker**: Ensure Docker is installed on your machine.
2. **Docker Compose**: Ensure Docker Compose is installed.
3. **Git**: For cloning and managing shared files.

---

## Configuration Steps

### 1. Creating the `docker-compose.yml` file

A `docker-compose.yml` file is present in the project with the following configuration:

```yaml
version: '3.8'
services:
  mysql:
    image: mysql:8.0
    container_name: mysql_shared
    restart: always
    environment:
      MYSQL_ROOT_PASSWORD: ${MYSQL_ROOT_PASSWORD}
      MYSQL_DATABASE: ${MYSQL_DATABASE}
      MYSQL_USER: ${MYSQL_USER}
      MYSQL_PASSWORD: ${MYSQL_PASSWORD}
    ports:
      - "${PORT_MAPPING}:3306" # Expose the configured port for MySQL
    volumes:
      - mysql_data:/var/lib/mysql
volumes:
  mysql_data:
```

Ensure the file uses environment variables for the credentials and port.

### 2. Creating the `.env` file

Create a `.env` file at the root of your project to define the environment variables:

```env
MYSQL_URL="jdbc:mysql://localhost:3307/shared_db"
MYSQL_ROOT_PASSWORD=YourROOTPASSW
MYSQL_DATABASE=shared_db
MYSQL_USER=define a user
MYSQL_PASSWORD=define a password
PORT_MAPPING=3307
```

These values will be automatically injected into your `docker-compose.yml` file.

### 3. Starting the Docker Container

Run the following command to start the MySQL service:

```bash
docker-compose up -d
```

This command will create and start the MySQL container in the background.

Verify that the container is running correctly with:

```bash
docker ps
```

---

### 4. Integration with Jakarta EE

#### a. Template of the `persistence.temp.xml` file

Use a temporary file to dynamically manage environment information:

```xml
<persistence xmlns="https://jakarta.ee/xml/ns/persistence"
             version="3.0">

    <persistence-unit name="jeUP">
        <class>com.projet.model.Player</class>
        <properties>
            <property name="jakarta.persistence.jdbc.url" value="$DB_URL" />
            <property name="jakarta.persistence.jdbc.user" value="$DB_USR" />
            <property name="jakarta.persistence.jdbc.password" value="$DB_PASS" />
            <property name="jakarta.persistence.jdbc.driver" value="com.mysql.cj.jdbc.Driver" />
            <property name="hibernate.dialect" value="org.hibernate.dialect.MySQLDialect" />
            <property name="hibernate.hbm2ddl.auto" value="update" />
            <property name="hibernate.show_sql" value="true" />
            <property name="hibernate.format_sql" value="true" />
        </properties>
    </persistence-unit>

</persistence>
```

#### b. Script to update the `persistence.xml` file

Run the Bash script by typing `./updateBDDInfoConnector.sh` in the terminal to inject the environment variables into the temporary file to generate the `persistence.xml` file.

```bash
updateBDDInfoConnector.sh
#!/bin/bash

# File paths
env_file=".env"
xml_template="src/main/resources/META-INF/persistence.temp.xml"
xml_output="src/main/resources/META-INF/persistence.xml"

# Load environment variables
export DB_URL=$MYSQL_URL
export DB_USR=$MYSQL_USER
export DB_PASS=$MYSQL_PASSWORD

# Substitute variables and generate the persistence.xml file
envsubst < $xml_template > $xml_output

echo "persistence.xml file successfully updated."
```

### 5. Test the Connection

1. Ensure the MySQL container is running.
2. Launch your Jakarta EE application and verify that the database connection works correctly.

---

### Additional Tips

- **Add `.env` to `.gitignore**: To avoid sharing your sensitive credentials in a public repository.
- **Check MySQL logs**: In case of errors, check the container logs with:
  ```bash
  docker logs mysql_shared
  ```
- **Dynamic Update**: Rerun the Bash script if environment variables change.

---

With these steps, your Docker configuration for MySQL is ready, and your Jakarta EE application is properly integrated.
