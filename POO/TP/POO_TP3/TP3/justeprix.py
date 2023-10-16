import random

from TP3 import utils, menu
import time

# Une partie est contenue dans une classe pour plus de simplicité et de lisibilité au niveau des variables.
class Game:
    # `minBound` et `maxBound` représente les valeurs minimale et maximale du nombre généré.
    # `maxAttempts` représente le nombre maximum d'essais pour l'utilisateur.
    def __init__(self, minBound:int=-100, maxBound:int=100, maxAttempts:int=15):
        self.minBound = minBound
        self.maxBound = maxBound
        self.number = random.randint(minBound, maxBound)
        self.maxAttempts = maxAttempts
        self.userAttempts = 1

    # Lance le jeu lui-même.
    # `debug`=True permet d'afficher le nombre mystère dès le début de la partie.
    def launchRounds(self, debug:bool=False):
        if debug is True:
            print(f"DEBUG MODE ON || Nombre généré: {self.number}")

        # Représente les rounds de jeu.
        while self.userAttempts <= self.maxAttempts:
            print(f"ESSAIS RESTANTS: {self.maxAttempts - self.userAttempts + 1}")

            userGuess = utils.getUserInput(self.minBound, self.maxBound, f"Quel est le nombre mystère? [{self.minBound}:{self.maxBound}] → ")

            # Condition de victoire du jeu (stoppe l'exécution de la fonction).
            if userGuess == self.number:
                self.isWon()
                return

            # Evaluation du nombre si le jeu n'a pas été gagné.
            elif userGuess > self.number:
                print("Le nombre est plus petit !")
            else:
                print("Le nombre est plus grand !")

            utils.jumpLines()
            self.userAttempts += 1

        # Si le programme arrive à ce point de la fonction, c'est que le joueur a perdu.
        # Condition de défaite.
        self.isLost()

    # Print le résultat du jeu. `waitingTime` permet un temps de pause avant la suite du programme.
    def isWon(self, waitingTime:float=1.3):
        print(f"Félicitations !! C'est une victoire en {self.userAttempts} essai(s) !")
        utils.jumpLines()
        time.sleep(waitingTime)
    def isLost(self, waitingTime:float=1.3):
        print(f"Raté... Le nombre mystère était {self.number}.")
        utils.jumpLines()
        time.sleep(waitingTime)

def main():
    userRetry = -1
    while userRetry != 0:
        menu.Box("L E  J U S T E  P R I X").drawBox()
        utils.jumpLines()

        # Fonction du jeu.
        Game().launchRounds()

        # Menu retry (0 = quitter, 1 = réessayer).
        userRetry = menu.drawBinaryMenu("Lancer une nouvelle partie ?")
        utils.jumpLines()