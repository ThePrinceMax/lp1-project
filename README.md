# LP1 Projet

Créé par : [Maxime Princelle](https://princelle.org)

[Lien vers le sujet](https://docs.google.com/document/d/1O9Cfxe6QZ-yc6G4GovpEqzZk6cyP_nfo3nQCdmnhhQA/edit)

## Lancement de l'application

Nous pouvons lancer l'application de deux manières différentes.

Depuis la racine du projet, lancer cette commande : 

```bash
mvn spring-boot:run
```

Depuis votre environnement de développement, lancer la méthode suivante : 
```java
Lp1ProjectApplication.main()
```

En tant que méthode lancée en "Standalone", qui va lancer le serveur intégré Tomcat sur le port 8080.

Après cela, il suffira de vous rendre à l'aide de votre client Web sur l'url suivante : 
```
http://localhost:8080/app/
```

```/app``` correspond à la route sur laquelle l'application est publiée.

## Configuration de l'application

La configuration de l'application, se déroule dans le fichier ```application.properties``` situé dans ```/src/main/resources/```

[Lien vers le fichier](src/main/resources/application.properties)

Dans ce fichier, vous pouvez configurer la connexion à votre base de données.

Voici un exemple avec la configuration suivante : 
- Adresse : localhost
- Port : 3306
- Base : "lp1Projet"
- Username : "root"
- Password : "flatflat"

```
spring.datasource.url = jdbc:mysql://localhost:3306/lp1Projet?useSSL=false
spring.datasource.username = root
spring.datasource.password = flatflat
```

Une fois la base de données renseignée et l'application lancée, cette dernière, via Hibernate, va générer tout ce qui est nécessaire à son fonctionnement.

## Technologies utilisées

Ce Projet Maven est basé sur : 
- Apache Tomcat
- Spring
- JPA / Hibernate avec MySQL Connector
- Jersey

