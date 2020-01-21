# LP1 Project - API

Oui, vous avez bien lu. Une simple API, en Java. (En cours de développement, pas encore en ligne)

Créé par : [Maxime Princelle](https://princelle.org)

[Lien vers le sujet](https://docs.google.com/document/d/1O9Cfxe6QZ-yc6G4GovpEqzZk6cyP_nfo3nQCdmnhhQA/edit)

__Sommaire__
- <a href="#comment-y-acc%C3%A9der-">Comment y accéder ?</a>
- <a href="#configuration">Configuration</a>
- <a href="#lancement">Lancement</a>
  - <a href="#docker">Docker</a>
- <a href="#routes">Routes</a>
- <a href="#technologies-utilis%C3%A9es">Technologies utilisées</a>

## Comment y accéder ?

__Projet encore en cours de développement...__

L'API sera, quand elle sera terminée, accessible via ce lien : 

[https://lp1.princelle.org/api](https://lp1.princelle.org/api)

Pour plus d'informations sur les routes, je vous invite à vous rendre <a href="#routes">ici</a>.

## Configuration

Une fois la base de données renseignée et l'application lancée, cette dernière, via Hibernate, va générer tout ce qui est nécessaire à son fonctionnement.

Pour la configuration, deux options s'offrent à vous. 

1) Vous souhaitez tout faire sur Docker (recommandé) :

Dans ce cas, veuillez suivre la partie sur [Docker](#docker).

2) Vous possédez déjà une base de données :

Dans ce cas, veuillez commencer par copier le fichier `.env.example` et le renommer en `.env`.

Une fois ouvert, vous obtiendrez le fichier suivant :

```sh
# Application
DATABASE_URL=mysql://${DATABASE_HOST}:${DATABASE_PORT}/${DATABASE_NAME}
DATABASE_NAME=lp1Project
DATABASE_USER=root
DATABASE_PASS=#__db_pass__#
DATABASE_HOST=lp1-mysql
DATABASE_PORT=3306

# MySQL
MYSQL_DATABASE=${DATABASE_NAME}
MYSQL_ROOT_PASSWORD=${DATABASE_PASS}

# JWT
JWT_SECRET=QhEEDgVhHpnehgVcypQNCyJYTTeTkPoncwHPBcJJFPWnJ3Pt76M49vZQioit
```

Pour changer les identifiants de connexion à la base de données, vous pouvez changer les champs suivants : 

- DATABASE_USER : `root` (si votre base est locale, mettez l'utilisateur disposant des droits sur votre base)
- DATABASE_PASS : `#__db_pass__#`
- DATABASE_NAME : `lp1Project` (le nom de votre base, __créée au préalable__ !)
- DATABASE_HOST : `lp1-mysql` (si votre base est locale, mettez `localhost`)
- DATABASE_PORT : `3306`

Pour finir la configuration, enregistrez votre fichier `.env` à la racine du projet.

## Lancement

Passons maintenant au lancement de l'application. 

Deux options s'offrent à vous : 

- Depuis la racine du projet, lancer cette commande : 

```bash
mvn spring-boot:run
```

- Depuis votre environnement de développement, lancer la méthode suivante :

```java
Lp1ProjectApplication.main()
```

En tant que méthode lancée en "Standalone", qui va lancer le serveur intégré Tomcat sur le port 8080.

--

Après cela, il suffira de vous rendre à l'aide de votre client Web sur l'url suivante : 

[http://localhost:8080/api/](http://localhost:8080/api/)

```/api``` correspond à la route sur laquelle l'API est publiée.

Pour voir toutes les routes de l'application, <a href="#routes">cliquez-ici</a>.

### Docker

#### Configuration de l'environnement

Si vous souhaitez démarrer cette application, ainsi que la base de données nécessaire à son fonctionnement, en une seule fois, un fichier docker-compose est mis à disposition. 

Avec cette base de données vous est également fourni un environnement phpMyAdmin afin de faciliter la gestion de cette dernière.

Pour commencer, veuillez copier le fichier `.env.example` et le renommer en `.env`.

Une fois ouvert, vous obtiendrez le fichier suivant :

```sh
# Application
DATABASE_URL=mysql://${DATABASE_HOST}:${DATABASE_PORT}/${DATABASE_NAME}
DATABASE_NAME=lp1Project
DATABASE_USER=#__db_user__#
DATABASE_PASS=#__db_pass__#
DATABASE_HOST=lp1-mysql
DATABASE_PORT=3306

# MySQL
MYSQL_DATABASE=${DATABASE_NAME}
MYSQL_RANDOM_ROOT_PASSWORD=yes
MYSQL_USER=${DATABASE_USER}
MYSQL_PASSWORD=${DATABASE_PASS}

# JWT
JWT_SECRET=QhEEDgVhHpnehgVcypQNCyJYTTeTkPoncwHPBcJJFPWnJ3Pt76M49vZQioit
```

Pour changer les identifiants de connexion à la base de données, vous pouvez changer les deux champs "DATABASE_USER" et "DATABASE_PASS" : 

- `#__db_user__#`
- `#__db_pass__#`

Pour finir, enregistrez votre fichier `.env` à la racine du projet.

#### Lancement de l'environnement

Afin de lancer l'environnement, exécutez la commande suivante : 

```docker-compose up -d```

##### API

Une fois fait, l'API sera déployée à l'adresse : 

[http://localhost:8080/api/](http://localhost:8080/api/)

```/api``` correspond à la route sur laquelle l'API est publiée.

Pour voir toutes les routes de l'application, <a href="#routes">cliquez-ici</a>.

##### phpMyAdmin

Pour accéder à phpMyAdmin, ce dernier se trouve sur le port 8084, vous pouvez y accédez via le lien suivant : 

[http://localhost:8084/](http://localhost:8084/)

## Routes

Comme dit précédemment, ```/api``` correspond à la route sur laquelle l'API est publiée.

Voici l'arborescence de l'application : 

- Users
  - GET ```/users``` : Liste tous les utilisateurs et leurs informations.
  - GET ```/users/{id}``` : Affiche les informations d'un utilisateur en fonction de son ID.
  - POST ```/users``` : Ajoute un utilisateur. 
  	<br/>__Réponse__ (JSON) : Renvoie l'utilisateur ajouté.
  	<br/>__Requête__ (JSON) : Attends les paramètres suivants (non optionnels) :
	```json
	{
		"firstName": "Maxime", //Obligatoire
		"lastName": "Princelle", //Obligatoire
		"emailId": "contact@princelle.org", //Obligatoire
		"password": "test1" //Obligatoire
	}
	```
  - PUT ```/users/{id}``` : Modifie les informations d'un utilisateur en fonction de son ID.
	<br/>__Réponse__ (JSON) : Renvoie l'utilisateur modifié.
  	<br/>__Requête__ (JSON) : Attends un ou plusieurs des paramètres suivants :
	```json
	{
		"firstName": "Maxime", //Optionnel
		"lastName": "Princelle", //Optionnel
		"emailId": "contact-maxime@princelle.org", //Optionnel
		"password": "test2" //Optionnel
	}
	```
  - DELETE ```/users/{id}``` : Supprime un utilisateur en fonction de son ID. 
  	<br/>__Réponse__ (JSON) : Renvoie un boolean confirmant sa suppression.
	```json
	{
    	"deleted": true
	}
	```

- Colocations
  - GET ```/colocs``` : Liste toutes les colocations et leurs informations.
  - GET ```/colocs/{id}``` : Affiche les informations d'une colocation en fonction de son ID.
  - POST ```/colocs``` : Ajoute une colocation. 
  	<br/>__Réponse__ (JSON) : Renvoie la colocation ajoutée.
  	<br/>__Requête__ (JSON) : Attends les paramètres suivants (non optionnels) :
	```json
	{
		"name": "L'équipeDe@W.", //Obligatoire
	}
	```
  - PUT ```/colocs/{id}``` : Modifie les informations d'une colocation en fonction de son ID.
	<br/>__Réponse__ (JSON) : Renvoie la colocation modifiée.
  	<br/>__Requête__ (JSON) : Attends un ou plusieurs des paramètres suivants :
	```json
	{
		"name": "L'équipeDe@H.", //Optionnel
	}
	```
  - DELETE ```/colocs/{id}``` : Supprime une colocation en fonction de son ID. 
  	<br/>__Réponse__ (JSON) : Renvoie un boolean confirmant sa suppression.
	```json
	{
    	"deleted": true
	}
	```

## Technologies utilisées

Ce projet Maven est basé sur : 
- Apache Tomcat
- Spring
- JPA / Hibernate avec MySQL Connector
- Jersey

----

[Remonter en haut](#lp1-project---api)
