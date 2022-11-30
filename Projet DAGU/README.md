# ProjetIF
## Projet Interfilière IATIC - MT - SEE - SNPI 2021-2022 - Véhicule Autonome : Dagu
## Projet DAGU - Module d'IA - Par Alexis Raffier (IATIC 4 | 2021 - 2022)

### Explications
- Ce module permet de tester l'IA directement sur un ordinateur (sous une distribution Linux), sans se soucier du reste de la chaîne de décision et des spécificités de la version embarquée sur la Jetson Nano.
- Pour cela, un script Python fourni (`simulation.py`) permet de simuler le transfert de données via le tube. 
- Le parallélisme est exploité directement dans le script Python, ce qui n'est pas le cas dans la version embarquée.
- L'IA reconnaît les objets suivants (tous sont des panneaux donnés dans le dossier `images_test`) : 50 (classe 1), route prioritaire (classe 2), cédez-le-passage (classe 3), stop (classe 4), sens interdit (classe 5) et danger (classe 6).
- Une vidéo de test est disponible, ainsi que la version annotée (après avoir été traitée par le module d'IA).

### Installation et tests
0. Prérequis : avoir installé `Python 3` et `pip` sur sa machine.
1. Lancer le script `install.sh` qui permet d'installer ce qui est nécessaire pour l'utilisation de Yolo. Il n'est nécessaire de le lancer qu'une seule fois, les tests suivants pourront être faits sans avoir à relancer ce script.
3. Nous vous conseillons de tester l'IA directement avec la caméra de votre ordinateur. Vous pouvez afficher sur un smartphone les photos fournies dans ce dossier et les passer devant votre caméra : l'IA doit pouvoir les reconnaître.
- Commencez par changer de dossier avec la commande `cd ./YOLOv5-Lite`
- Vous pouvez ensuite lancer l'IA avec une des commandes suivantes :
-- Avec la caméra :
	`python3 detect.py --source 0 --weights ../best.pt --conf 0.5 --classes 1 2 3 4 5 6`
-- Avec la vidéo de test fournie :
	`python3 detect.py --source ./../images_test/video_test.mp4 --weights ../best.pt --conf 0.5 --classes 1 2 3 4 5 6`
- *Note 1 : le paramètre "conf" permet d'indiquer un seuil de confiance. L'IA n'affichera l'objet détecté que si l'indice de confiance de la détection est supérieure à ce seuil. Vous pouvez modifier ce paramètre pour voir les différences dans l'IA (le paramètre doit être compris entre 0 et 1).*
- *Note 2 : les paramètres "classes" indiquent que nous ne détectons que 6 objets sur les 7 sur lesquels l'IA est entraînée. La classe 0 correspond à la détection d'autres véhicules DAGU, mais lors de nos tests nous avons pu voir que cela ne fonctionnait pas correctement. Nous avons décidé de l'éliminer de la détection pour de meilleurs résultats.*
- *Note 3 : les résultats de la détection, à savoir la vidéo qui a été tournée, est ensuite enregistrée dans le dossier `./runs/detect/exp*`, `*` étant un nombre qui s'incrémente à chaque test effectué. Il est possible de ne pas enregistrer le résultat avec le paramètre `--nosave`.*
- *Note 4 : pour stopper le script d'IA, il est nécessaire de lancer `CTRL + C`.*
- *Note 5 : La version fournie a été pensée pour être utilisée avant tout avec la webcam. Ainsi la sortie donnée sur le terminal ne sera pas optimale avec l'utilisation d'images ou de vidéos enregistrées. Cependant cela reste fonctionnel et les résultats seront visibles dans le dossier `./runs/detect/exp*` comme expliqué plus haut. N'hésitez pas à tester avec la vidéo de test fournie.*

