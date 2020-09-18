Projet n°10 pour OpenClassrooms

- Licence : License libre
- Auteur : Jérôme Deneux

---------------------
Description du projet
---------------------

Application Web APIREST pour les utilisateurs des bibliothèques d'une grande ville.

1e Release du projet :

 - Application Web avec la possibilité de rechercher un ouvrage, voir le nombre d'exemplaire disponible et leurs disponibilités, consulter ses emprunts ses emprunts en cours, et pouvoir les prolonger de 4 semaines si cela n'a déjà été fait.
 
 - Réaliser un batch pour envoyer un email aux utilisateurs qui n'ont pas rendu les ouvrages dans le temps imparti à la fréquence d'une fois par jour.
 
 2e Release du projet :
 
 - Correction de l'application en suivant 3 tickets émis par le client.
 
 ----------
Pré-requis
----------

Vous devez posséder Java JRE version 8 ou supérieur sur votre machine pour pouvoir correctement utiliser l'application.
 
----------------------
Technologies employées
----------------------
Langage :
- Java 8
- TypeScript

Front : 
- Angular

Back :
- Spring Data
- Spring Web
- Spring Mail
- Spring Security
- Zuul
- Eureka
- Lombok
- MapStruct

Base de données :
- PostgreSQL

Serveur d'application :
Apache Tomcat v.9

Restaurer la base de données
----------------------------

Au lancement de l'application, le fichier data.sql présent dans chaques dossiers ressources va automatiquement initier la base de donnée.


--------------------------------------------
Installation et utilisation de l’application
--------------------------------------------

Installation
------------

-	Ouvrez votre IDE, et importez le projet extrait depuis le fichier .zip ou clonez le fichier depuis le repository :  https://github.com/Zyknafein92/library
-	Importez les fichiers avec Maven.
- Lancez "npm install" dans le terminal du micro-service "libraryfront"
- Rendez-vous dans << Project Structure >>
- Allez dans l'onglet module
- Sélectionnez l'un des micro-services
- Allez dans l'onglet source
- Cliquez sur "Add Content Root"
- Dans le dossier library, sélectionnez le fichier correspondant au nom du micro-service sélectionné et cliquez sur "ok"
- Rendez-vous ensuite dans dépendencies puis cliquez sur module sdk, et sélectionnez la version 8 de java ou une supérieur.
- Cliquez sur "Apply"
- Répétez l'opération pour tous les services.

Lancez les tests
----------------
Pour lancer les tests de l'application, sélectionnez un micro-service, cliquez droit dessus puis rendez-vous sur le menu roulant "Run Maven" puis allez sur "test" et validez.


Démarrez l'application votre IDE (dev mod)
------------------------------------------
-	Lancez « eurekamicroservice»
-	Lancez « gatewaymicroservice »
-	Lancez « bookmicroservice »
-	Lancez « usermicroservice »
-	Lancez « librarymicroservice »
-	Lancez « borrowmicroservice »
-	Lancez « AngularCLI »
-	Rendez-vous sur http://localhost:4200

Déployer l'application (prod mod)
---------------------------------
- Sélectionnez un micro-service puis cliquez droit dessus, puis rendez-vous sur le menu roulant "Run Maven" et sélectionnez "package"
- Réalisez la même manipulation sur tous les micro-services sauf libraryfront
SpringBoot embarquant un serveur d'application TomCat, vous n'avez plus qu'à déployer les micro-services grâce à l'aide d'un éditeur de commande et la commande : java -jar nom_du_microservice.jar.

/!\ Il est important de lancer le microservice " eureka-microservice " et " gateway-microservice " en premier, les autres micro-services ne requiert pas d'ordres précis. /!\

Pour le micro-service libraryfront, vous devez réaliser les manipulations suivantes :

- Ouvrez le terminal sur votre IDE dans le micro-service libraryfront
- Exécutez la commande suivante "ng build --prod"
- Rendez-vous dans le fichier contenant le projet, puis dans le dossier contenant libraryfront
- Ouvrez le fichier dist, et copier son contenu
- Rendez-vous ensuite dans le dossier contenant Tomcat 9.0 puis dans webapps
- Coller le fichier précédemment copier.
- Ouvrez le fichier index.html avec notePad ++
- Remplacez <base href="/"> par <base href="libraryfront">
- Rendez-vous ensuite sur http://localhost:4200/libraryfront







