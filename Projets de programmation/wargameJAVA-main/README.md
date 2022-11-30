# Wargame JAVA
## Projet programmation orientée objet

## Vidéos

### Trailer

https://youtu.be/P19JDrzMVDQ

### Présentation

https://youtu.be/ytn9o-oi_UE

## Musique

Crédits musique : Darren Korb - Hades - Original Soundtrack - 02 The House of Hades

## Auteurs
* CORBEIL Alex
* KHIARI Slim
* RAFFIER Alexis
* TEA Jacques
* VIDAL Antoine

ISTY | IATIC 3 | 2020-2021

## Commandes :

### Créer le fichier .jar
```bash
javac fr/wargame/controleur/main/Main.java
jar cfm wargame.jar manifest fr plateau.txt audio/ sprites/
find . -name "*.class" | xargs rm -f
```

### Lancer le jeu à partir du fichier .jar
```bash
java -jar wargame.jar
```
