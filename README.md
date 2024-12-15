# Mario Super Hop

## Description

Mario Super Hop est un jeu développé dans le cadre du projet HOP pour la matière IPO en L2 Informatique à l'Université Paris-Saclay. Le jeu permet au joueur de contrôler Mario et de l'amener à sauter pour éviter des obstacles.

Ce projet a été réalisé par **Mokhtari Adel** et **Bentahar Abdelrahmen**, étudiants en L2 Informatique, groupe 3.

## Commandes

Voici les différentes commandes disponibles pour manipuler le projet via `make` :

- `make` : Lance le jeu.
- `make compile` : Compile les fichiers sources du projet.
- `make execute` : Exécute le programme une fois qu'il est compilé.
- `make clean` : Nettoie les fichiers `.class` générés lors de la compilation.
- `make push` : Envoie les fichiers sur le dépôt Git.

## Documentation Javadoc

La documentation complète du projet est disponible sous forme de **Javadoc**. Vous pouvez la consulter en suivant les étapes ci-dessous :

1. La documentation Javadoc est générée dans le dossier `doc/javadoc`.
2. Pour générer la documentation, il suffit d'exécuter la commande suivante (après avoir compilé le projet) :
   ```bash
   javadoc -cp lib/gson-2.8.9.jar -d doc/javadoc src/*.java
   ```
3. Une fois la documentation générée, vous pouvez la consulter en ouvrant le fichier `index.html` situé dans le dossier `doc/javadoc` avec un navigateur web.

## Prérequis

- Assurez-vous d'avoir **make** installé sur votre machine.
- Le projet utilise **Java**, donc vous devez avoir **JDK** installé pour compiler, exécuter le programme et générer la documentation Javadoc.

## Installation

1. Clonez le dépôt Git sur votre machine :
   ```bash
   git clone https://github.com/addin91/HOP.git
   ```
2. Accédez au répertoire du projet :
   ```bash
   cd Hop
   ```

## Utilisation

- Pour lancer le jeu, utilisez la commande `make` dans le terminal.
- Pour compiler le projet, utilisez `make compile`.
- Pour exécuter le programme après la compilation, utilisez `make execute`.
- Pour nettoyer les fichiers `.class` générés, utilisez `make clean`.
- Pour envoyer les modifications sur Git, utilisez `make push`.
- Pour générer la documentation Javadoc, utilisez `javadoc -cp lib/gson-2.8.9.jar -d doc/javadoc src/*.java` après avoir compilé le projet.

## Auteurs

- **Mokhtari Adel**
- **Bentahar Abdelrahmen**

## Université Paris-Saclay

Projet réalisé pour la matière IPO, groupe 3, étudiants en L2 Informatique.
