# LP1 Projet

Créé par : [Maxime Princelle](https://princelle.org)

[Lien vers le sujet](https://docs.google.com/document/d/1O9Cfxe6QZ-yc6G4GovpEqzZk6cyP_nfo3nQCdmnhhQA/edit)

## Lancement

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

Pour voir toutes les routes de l'application, <a href="#routes">cliquez-ici</a>.

## Configuration

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

## Routes

Comme dit précédemment, ```/app``` correspond à la route sur laquelle l'application est publiée.

Voici l'arborescence de l'application : 

- ```/api```

- Users
  - GET ```/users``` : Lister tous les utilisateurs et leurs informations.
  - GET ```/users/{id}``` : Afficher les informations d'un utilisateur en fonction de son ID.
  - POST ```/users``` : Ajouter un utilisateur. 
  	<br/>__Réponse__ (JSON) : Renvoie l'utilisateur ajouté.
  	<br/>__Requête__ (JSON) : Attends les paramètres suivants (non optionnels) :
	```json
	{
		"firstName": "Maxime", // Obligatoire
		"lastName": "Princelle", // Obligatoire
		"emailId": "contact@princelle.org" // Obligatoire
	}
	```
  - PUT ```/users/{id}``` : Modifie les informations d'un utilisateur en fonction de son ID.
	<br/>__Réponse__ (JSON) : Renvoie l'utilisateur modifié.
  	<br/>__Requête__ (JSON) : Attends un ou plusieurs des paramètres suivants :
	```json
	{
		"firstName": "Maxime", // Optionnel
		"lastName": "Princelle", // Optionnel
		"emailId": "contact-maxime@princelle.org" // Optionnel
	}
	```
  - DELETE ```/users/{id}``` : Supprime un utilisateur en fonction de son ID. 
  	<br/>__Réponse__ (JSON) : Renvoie un boolean confirmant sa suppression.
	```json
	{
    	"deleted": true
	}
	```
