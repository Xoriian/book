# Script utilisé pour simuler un script recevant les données du tube
# Auteur : Alexis Raffier

import pipe

# Permet de lire en continue dans le tube
#La fonction "listener" étant bloquante, la lecture ne se fera que lorsque le tube n'est pas vide
def receveur():
    with open(path_fifo, 'r') as fifo:
        while(True):
            flags = pipe.listener(fifo)
            print("J'ai reçu :", flags)


if __name__ == "__main__":
    path_fifo = pipe.path
    receveur()