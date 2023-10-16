from TP3 import utils, menu

# L'arbre est contenu dans une classe pour plus de simplicité et de lisibilité au niveau des variables.
class ChristmasTree:
    # `height` définit la hauteur de l'arbre, `spaceChar` définit le caractère utilisé hors de l'arbre,
    # `fillChar` définit le caractère utilisé hors de l'arbre et `basePadding` permet de décaler
    # l'arbre du bord de l'écran (s'applique à gauche et à droite).
    def __init__(self, height: int, spaceChar: str = " ", fillChar: str="*", basePadding:int=3):
        self.height = height
        self.spaceChar = spaceChar[0]
        self.fillChar = fillChar
        self.basePadding = basePadding

    # Si une textBox est renseignée et que l'arbre est plus petit que celle-ci, `centerWithBox` permet
    # d'ajouter un padding supplémentaire pour centrer l'arbre par rapport au centre de la textBox.
    # Si la box est plus grande que l'arbre, `textBoxIsResizable`=True permet d'agrandir la textBox
    # à la largeur max de l'arbre.
    def drawTree(self, textBox:menu.Box=None, centerWithBox=True, textBoxIsResizable=False):
        # `maxWidth` représente la largeur maximale de l'arbre [AVEC SON `basePadding`].
        maxWidth = self.height * 2 + self.basePadding*2 - 1
        extraPadding = 0

        if bool(textBox):
            # Centre l'arbre par rapport à la box.
            if centerWithBox is True and maxWidth < textBox.width+2:
                extraPadding = int(textBox.width+2-maxWidth)
            # Redimensionne la box par rapport à l'arbre.
            if textBoxIsResizable is True:
                textBox.width = max(textBox.width, maxWidth-2)

            # Dessine la textBox si elle a été renseignée.
            textBox.drawBox()
            utils.jumpLines()

        # Dessine l'arbre lui-même.
        for row in range(self.height):
            print((self.fillChar*(row*2+1)).center(maxWidth+extraPadding, self.spaceChar))
        print(self.fillChar.center(maxWidth+extraPadding))
        print((self.fillChar*3).center(maxWidth+extraPadding))
        utils.jumpLines()

def main():
    userRetry = -1
    while userRetry != 0:
        menu.Box("L E  S A P I N  D E  N O Ë L").drawBox()
        utils.jumpLines()

        # Demande à l'utilisateur la taille de l'arbre (Min: 4, max: 25).
        treeHeight = utils.getUserInput(4, 25, "Hauteur de l'arbre ? → ")
        utils.jumpLines()

        ChristmasTree(treeHeight).drawTree(menu.Box("T A D A A A A  ♪", title=""), textBoxIsResizable=True)

        # Menu retry (0 = quitter, 1 = réessayer).
        userRetry = menu.drawBinaryMenu("Voulez vous dessiner un autre arbre ?")
        utils.jumpLines()