# Script gérant le tube utilisé pour communiquer les données de détection de l'IA (via le script "detect.py")
# Auteur : Alexis Raffier

import os

path = "/tmp/fifo"

# Permet de créer un tube nommé pour le transfert de données
def create_fifo():
    try:
        os.mkfifo(path)
    except:    
        print("FIFO already created")

# Permet d'écrire les données 'flags' dans le tube 'fifo'
def talker(fifo, flags):
    fifo.write(flags)
    try:
        fifo.flush()
    except:
        pass

# Permet de lire les données du tube 'fifo' et les retourne sous forme d'entier
# Les données transmises dans le tube sont formatées pour faire exactement 4 caractères
def listener(fifo):
    flags = fifo.read(4) # La fonction est bloquante : ne renvoie une valeur que lorsque le pipe n'est pas vide
    return int(flags)

# Permet de supprimer le tube nommé du sustème
def remove_fifo():
    try:
        os.remove(path)
    except:
        print("FIFO can't be removed")
