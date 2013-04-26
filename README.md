# Projet de Sécurité Crypto ![Application Sécurité Crypto](src/res/icon-black.png) 

__Sujet 6 : chriffrage partiel de fichiers__

Auteurs :
 * Nicolas Devenet
 * Valériane Jean

***

## Introduction

Le sujet demandait la réalisation d’une application graphique, permettant de chiffrer partiellement un document texte.  
L’application a été réalisée avec NetBeans en utilisant les composants graphiques Swing.


## Utilisation

L’utilisateur a le choix entre trois possibilités lors de l’ouverture de l’application.

 1. L’onglet « *Crypt a file* » permet de crypter un fichier, en précisant les lignes à chiffrer, le mot de passe à utiliser, et le dossier de destination.  
 Deux fichiers sont générés. Le premier contient tout le texte qui ne doit pas être crypté, et un marqueur est ajouté à la fin ; le second contient le texte crypté.
 2. L’onglet « *Decrypt a file* » permet de décrypter les fichiers, en indiquant le mot de passe utilisé. Il suffit de sélectionner le premier fichier généré, l’application se chargera de trouver le second fichier crypté.  
 Le fichier généré recompose le fichier initial, en y insérant le texte décrypté.
 3. L’onglet « *Display a file* » permet à un utilisateur ne connaissant pas le mot de passe d’afficher tout de même le texte non crypté.  
 Par défaut, l'extension de fichier autorisée est celle du fichier généré par l’application, mais on peut forcer la lecture de n’importe quel fichier.


## Code source

Le code source du projet est disponible sur GitHub, à l’adresse suivante :  
[https://github.com/nicolabricot/TP-Crypto-2013](//github.com/nicolabricot/TP-Crypto-2013)

 * Le dossier `dist/` contient la JavaDoc, générée automatiquement depuis NetBeans, ainsi qu’un JAR (et les librairies nécessaires) de l’application.  
 __Il suffit de lancer `Projet-Crypto.jar` pour lancer l’application.__
 * Le dossier `doc/` contient la documentation. Le [sujet](//github.com/nicolabricot/TP-Crypto-2013/blob/master/doc/Sujet.pdf?raw=true), le [rapport](//github.com/nicolabricot/TP-Crypto-2013/blob/master/doc/Rapport.pdf?raw=true) et les sources utilisées sont aussi disponibles.
 * Le dossier `lib/` contient les librairies utilisées.
 * Le dossier `src/` contient les sources commentées.
 * Le dossier `tests/` contient des documents textes utilisés pour tester l’application.


## Illustrations

![Onglet "Crypt a file"](doc/screenshots/crypt-tab-completed.png)

![Onglet "Derypt a file"](doc/screenshots/decrypt-tab-completed.png)

![Onglet "Display a file"](doc/screenshots/display-tab-completed.png)


## Sources

Certaines méthodes ou implémentations utilisées sont directement inspirées de ressources trouvées sur Internet.

 * Lire un fichier  
 http://tomtomgeek.blogspot.fr/2011/09/java-lire-un-fichier.html
 * Nombre de lignes dans un fichier  
 http://stackoverflow.com/questions/453018/number-of-lines-in-a-file-injava
 * Lire la dernière ligne d'un fichier  
 http://fr.softuses.com/94739
 * Créer une fenêtre de sélection de fichiers/dossiers  
 http://www.zentut.com/java-swing/jfilechooser/
 * Icône trousseau de clef  
 http://thenounproject.com/noun/key
