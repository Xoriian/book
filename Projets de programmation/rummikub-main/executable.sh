#!/bin/bash

# Rummikub - IATIC 3 - Groupe 9
# Ce fichier permet de compiler et d'exécuter le jeu.
# Sans argument et si l'exécutable n'existe pas, ou avec l'argument -r, les fichiers sont compilés et l'exécutable est créé.
# Dans tous les cas, le programme est exécuté.

cd CodeSource/Controleur
if [ "$1" = "-r" ] || [ ! -f "play.run" ]
then
	echo -n "Compilation... "
	rm -f play.run
	gcc *.c ../Vue/*.c ../Modele/*.c -Wall -lSDL_ttf `sdl-config --libs` -o play.run
	echo "terminée"
fi
echo "Lancement du jeu"
./play.run
cd ../..

