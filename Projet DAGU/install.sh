# Script d'installation des prérequis pour utiliser Yolo
# Auteur : Alexis Raffier

#!/bin/bash

# Installation du dossier de Yolo_v5-Lite via Github :
git clone https://github.com/ppogg/YOLOv5-Lite

# Déplacer les fichiers supplémentaires fournis directement :
mv detect.py pipe.py simulation.py simulation.sh requirements.txt ./YOLOv5-Lite
mv datasets.py ./YOLOv5-Lite/utils

# Installation des dépendances pour utiliser l'IA :
pip install -r YOLOv5-Lite/requirements.txt

# L'IA fournie est déjà entraînée. Il suffit alors d'utiliser les "poids" best.pt fournis.
echo -e "\n\n\n\nL'IA se lance avec la commande suivante depuis le dossier YOLOv5-Lite :"
echo "python3 detect.py --source 0 --weights ../best.pt --conf 0.5 --classes 1 2 3 4 5 6"
