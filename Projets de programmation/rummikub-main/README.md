ISTY | IATIC 3 | 2020-2021
Notice d'instruction du Rummikub

Sommaire :
1) Description du projet
2) Comment installer et faire fonctionner ce jeu ?
	a) Si vous n'avez pas installé la SDL 1.2
	b) Si vous avez installé la SDL 1.2
3) Règles et fonctionnement du jeu
4) Crédits et outils utilisés


1) Description du projet
Dans le cadre du projet algorithmique de IATIC 3 (2020-2021), nous avons réalisé un jeu de Rummikub en langage C et utilisant une interface graphique.


2) Comment installer et faire fonctionner ce jeu ?
Le Rummikub utilise la SDL 1.2 qui est une bibliothèque libre permettant d'afficher des fenêtres et de les gérer.
Cette bibliothèque, ainsi que certaines extensions, sont obligatoires pour faire fonctionner ce jeu. Si vous l'avez déjà installée, vous pouvez passer directement au 2)b).


	a) Si vous n'avez pas installé la SDL 1.2
*** DEBIAN et dérivés (UBUNTU) ***
Pour installer la SDL 1.2 sur un système dérivé de Debian (Debian, Ubuntu...), il est nécessaire de se procurer les paquets suivants :
- libsdl1.2debian (gestion de l'interface graphique, SDL)
- libsdl1.2-dev (SDL, fichiers de développement)
- libsdl-image1.2 (gestion des images, SDL_IMAGE)
- libsdl-image1.2-dev (SDL_IMAGE, fichiers de développement)

- libsdl-ttf2.0-0 (gestion du texte, SDL_TTF)
- libsdl-ttf2.0-dev (SDL_TTF, fichiers de développement)

Pour une distribution dérivée de Debian, la ligne suivante permet d'installer et/ou de mettre à jour les paquets nécessaires. Il est nécessaire d'exécuter cette commande sur un compte possédant les droits "sudo").
	sudo apt-get update && sudo apt-get install libsdl1.2debian libsdl1.2-dev libsdl-image1.2 libsdl-image1.2-dev libsdl-ttf2.0-0 libsdl-ttf2.0-dev

*** AUTRES ***
Pour les autres distributions, veuillez-vous reporter aux instructions propres à votre système, sur le site de la SDL, en vous assurant que vous installez la SDL 1.2.


	b) Si vous avez installé la SDL 1.2
Une fois que vous avez installé la SDL 1.2 (ainsi que SDL_IMAGE et SDL_TTF), il ne reste qu'à compiler et lancer le programme à l'aide d'un terminal.
- Se rendre dans le dossier où se trouvent les fichiers du jeu 
- Exécuter la commande suivante qui permet d'obtenir les droits d'exécution sur le script qui permet de compiler le jeu
	chmod a+x executable.sh
- Ensuite, exécuter le script, le jeu se lance
	./executable.sh
- Enfin, pour pouvoir lancer de nouveau le jeu sans compiler
	./executable.sh
- Pour compiler à nouveau, il faut utiliser l'argument -r
	./executable.sh -r


3) Règles et fonctionnement du jeu
- En arrivant à l'écran-titre, vous pouvez cliquer sur le rouage en bas à droite pour régler les paramètres de joueurs et de manches, ou cliquer sur le trophée en bas à gauche pour accéder à l'historique des scores des 10 dernières parties.
- Appuyez sur le bouton "play" pour lancer le jeu. On vous demande alors d'entrer les pseudos des joueurs humains.
- 4 tuiles apparaissent en face des joueurs. Celui qui a la plus tuile avec le plus grand numéro commence. S'il y a égalité, alors le joueur le plus en haut commence.
- Pour poser une tuile, cliquez sur la tuile à jouer (dans votre main ou une du plateau), puis sur une des tuiles de la série où vous voulez la poser. La tuile sera automatiquement mise à la bonne place. Vous pouvez aussi cliquer sur la case vide après une série. Pour créer une nouvelle série, cliquez sur une case du plateau où il n'y a pas de série.
- Si vous n'avez pas encore posé de tuiles, vous ne pouvez pas poser de tuile sur les séries déjà réalisées ou les réarranger : vous devez en créer au moins une nouvelle avec les tuiles de votre main. La somme des numéros des tuiles jouées doit dépasser 30.
- Si vous avez déjà posé des tuiles, alors vous pouvez réarranger les séries comme vous le souhaitez.
- Appuyez sur le bouton vert pour valider votre coup si vous avez joué une ou plusieurs tuiles. S'il n'est pas valide, alors vous reprendrez votre tour du début. Appuyez sur le bouton rouge pour recommencer votre tour. Appuyez sur le bouton en forme de pioche pour terminer votre tour en piochant, sans poser.
- Si la main d'un des joueurs est vide, ou bien si la pioche est vide, alors le jeu s'arrête et les scores sont calculés. 
- Une fois toutes les manches finies, vous accédez à l'écran des scores. Vous pouvez appuyer sur le bouton retour pour quitter le jeu.


4) Crédits et outils utilisés
Auteurs : CORBEIL Alex - RAFFIER Alexis - TALHA Elhassane - TEA Jacques - VIDAL Antoine

\\\ Outils utilisés
>>> Bibliothèque : SDL 1.2
>>> Compilateur : GCC 8.3.0
>>> Débogueur - Détecteur de fuites mémoires : Valgrind 3.14.0
>>> Éditeur de texte : Geany 1.33
>>> Éditeur de texte : Vim 8.1
>>> Langage : C
>>> Logiciel de suivi de modifications : Git 2.20.1
>>> Service d'hébergement Github
