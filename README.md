# Configuration de la Base de Données MySQL avec Docker

Ce document fournit des instructions pour configurer une base de données MySQL à l'aide de Docker et pour intégrer les informations de connexion dans une application Jakarta EE.

## Prérequis

1. **Docker** : Assurez-vous que Docker est installé sur votre machine.
2. **Docker Compose** : Assurez-vous que Docker Compose est installé.
3. **Git** : Pour le clonage et la gestion des fichiers partagés.

---

## Étapes de Configuration

### 1. Création du fichier `docker-compose.yml`

Un fichier `docker-compose.yml` est présent dans le projet avec la configuration suivante :

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
      - "${PORT_MAPPING}:3306" # Expose le port configuré pour MySQL
    volumes:
      - mysql_data:/var/lib/mysql
volumes:
  mysql_data:
```

Assurez-vous que le fichier utilise des variables d'environnement pour les identifiants et le port.

### 2. Création du fichier `.env`

Créez un fichier `.env` à la racine de votre projet pour y définir les variables d'environnement :

```env
MYSQL_URL="jdbc:mysql://localhost:3307/shared_db"
MYSQL_ROOT_PASSWORD=VotreROOTPASSW
MYSQL_DATABASE=shared_db
MYSQL_USER=un user à definir
MYSQL_PASSWORD=un password à definir
PORT_MAPPING=3307
```

Ces valeurs seront automatiquement injectées dans votre fichier `docker-compose.yml`.

### 3. Démarrage du conteneur Docker

Lancez la commande suivante pour démarrer le service MySQL :

```bash
docker-compose up -d
```

Cette commande créera et lancera le conteneur MySQL en arrière-plan.

Vérifiez que le conteneur fonctionne correctement avec :

```bash
docker ps
```

---

### 4. Intégration avec Jakarta EE

#### a. Modèle du fichier `persistence.temp.xml`

Utilisez un fichier temporaire pour gérer dynamiquement les informations d'environnement :

```xml
<persistence xmlns="https://jakarta.ee/xml/ns/persistence"
             version="3.0">

    <persistence-unit name="jeUP">
        <class>com.projet.model.Joueur</class>
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

#### b. Script de mise à jour du fichier `persistence.xml`

Executez le script Bash en écrivant sur le terminal `./updateBDDInfoConnector.sh` pour injecter les variables d'environnement dans le fichier temporaire pour générer le fichier `persistence.xml` final :

```bash
updateBDDInfoConnector.sh
#!/bin/bash

# Chemins des fichiers
env_file=".env"
xml_template="src/main/resources/META-INF/persistence.temp.xml"
xml_output="src/main/resources/META-INF/persistence.xml"

# Charger les variables d'environnement
export DB_URL=$MYSQL_URL
export DB_USR=$MYSQL_USER
export DB_PASS=$MYSQL_PASSWORD

# Substitution des variables et génération du fichier persistence.xml
envsubst < $xml_template > $xml_output

echo "Fichier persistence.xml mis à jour avec succès."
```

### 5. Testez la Connexion

1. Assurez-vous que le conteneur MySQL est en cours d'exécution.
2. Lancez votre application Jakarta EE et vérifiez que la connexion à la base de données fonctionne correctement.

---

### Conseils Supplémentaires

- **Ajoutez `.env` à `.gitignore`** : Pour ne pas partager vos identifiants sensibles sur un dépôt public.
- **Vérifiez les logs MySQL** : En cas d'erreur, consultez les logs du conteneur avec :
  ```bash
  docker logs mysql_shared
  ```
- **Mise à jour dynamique** : Réexécutez le script Bash en cas de changement des variables d'environnement.

---

Avec ces étapes, votre configuration Docker pour MySQL est prête et votre application Jakarta EE est correctement intégrée.

