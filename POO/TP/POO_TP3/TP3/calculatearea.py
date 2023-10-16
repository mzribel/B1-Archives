import math
from TP3 import utils, menu
from tkinter import *

class RegularPolygon:
    def __init__(self, sideCount, sideLength):
        if sideCount < 3:
            raise Exception("A polygon cannot have less than 3 sides.")
        if sideLength< 0:
            raise Exception("A polygon must have a positive sidelength.")

        self.sideCount = sideCount
        self.sideLength = sideLength
        self.Area = self.getArea()
        self.vertexAngle = self.getVertexAngle()

    # Fonction mathématique retournant l'aire d'un polygone régulier à partir de son nombre de côtés (`sideCount`)
    # et de la longueur d'un de ses côtés (`sideLength`).
    # Valeur arrondie à la `roundValue`-ème décimale, "None" = arrondit à l'entier.
    def getArea(self, roundingValue=3):
        return round(self.sideCount * (self.sideLength ** 2) / (4 * math.tan(math.radians(180) / self.sideCount)), roundingValue)

    # Retourne la valeur d'un angle interne du polygone.
    # Valeur arrondie à la `roundValue`-ème décimale, "None" = arrondit à l'entier.
    def getVertexAngle(self, roundingValue=None):
        return round((self.sideCount-2)*180/self.sideCount, roundingValue)

    # Dessine le polygone et affiche ses informations dans une fenêtre externe (TKinter)
    def drawPolygon(self):
        TKWindow().drawPolygon(self.sideCount, self.sideLength, self.Area, self.vertexAngle)

# Permet de générer les points formant un polygone de `sideCount` côtés.
# `radius` représente le rayon du cercle autour du polygone,
# `xOffset` et `yOffset` représente la position de l'origine de ce cercle.
def getPoints(sideCount, radius, xOffset, yOffset):
    points = []
    for i in range(sideCount):
        x = round(xOffset + radius * math.cos(2 * math.pi * i / sideCount))
        y = round(yOffset + radius * math.sin(2 * math.pi * i /sideCount))
        points.extend([x, y])
    return points

class TKWindow:
    # Initialise les valeurs de base de la fenêtre.
    def __init__(self, height=430, width=500, bgColor="#131313", fgColor="white", secColor="cyan"):
        self.height = height
        self.width = width
        self.bgColor= bgColor
        self.fgColor = fgColor
        self.secColor = secColor

        self.root = Tk()
        self.root.geometry(f"{width}x{height}")
        self.root.config(background=self.bgColor)
        self.root.resizable(False, False)

    # Dessine un polygone et donne ses caractéristiques dans la fenêtre.
    def drawPolygon(self, sideCount, sideLength, area, vertexAngle):
        self.root.title("Visualiseur de polygone")

        class GUI(Canvas):
            def __init__(self, master, *args, **kwargs):
                Canvas.__init__(self, master=master, *args, **kwargs)

        polyPoints = getPoints(sideCount, 100, 105, 105)

        # Création du polygone.
        polygon = GUI(self.root)
        polygon.create_polygon(polyPoints, outline=self.secColor, fill=self.bgColor)
        polygon.configure(bd=0, highlightthickness=0, relief='ridge')
        polygon.config(background=self.bgColor)
        polygon.place(x=145, y=0)

        # Création des zones de texte sous le polygone.
        Label(self.root, text="POLYGONE GENERE:", font=("Arial", 12, "bold"), bg=self.bgColor, fg=self.fgColor, padx=10).place(x=0,
                                                                                                                  y=225)
        Label(self.root, text=f"→ Nombre de côtés: {sideCount}", font=("Arial", 11, "bold"), bg=self.bgColor, fg=self.fgColor, padx=30,
              pady=5).place(x=0, y=250)
        Label(self.root, text=f"→ Longueur d'un côté: {sideLength}m", font=("Arial", 11, "bold"), bg=self.bgColor, fg=self.fgColor, padx=30,
              pady=5).place(x=0, y=275)
        Label(self.root, text=f"→ Angle entre chaque côté: {vertexAngle}°.", font=("Arial", 11, "bold"), bg=self.bgColor, fg=self.fgColor,
              padx=30, pady=5).place(x=0, y=300)
        Label(self.root, text=f"→ Aire du polygone: {area}m².", font=("Arial", 11, "bold"), bg=self.bgColor, fg=self.secColor, padx=30,
              pady=5).place(x=0, y=325)
        Label(self.root, text="FERMEZ LA FENÊTRE POUR CONTINUER.", font=("Arial", 8, "italic"), bg=self.bgColor, fg="gray",
              padx=30, pady=5).place(x=110, y=390)

        self.root.mainloop()


def main():
    userRetry = -1
    while userRetry != 0:
        menu.Box("C A L C U L E R  L ' A I R E").drawBox()
        utils.jumpLines()

        # Demande à l'utilisateur les dimensions de son polygone régulier.
        sideLength = utils.getUserInput(0, 150, "Valeur de la mesure en mètres d'un côté: ", "float")
        sideCount = utils.getUserInput(3, 20, "Nombre de côtés du polygone: ")

        # Calcule l'aire du polygone et retourne le résultat.
        polygon = RegularPolygon(sideCount, sideLength)
        print(f">> Aire du polygone: {polygon.Area}m².\n")

        # Permet de visualiser le polygone dans une fenêtre externe.
        # Le programme se met en pause tant que la fenêtre n'a pas été fermée.
        if menu.drawBinaryMenu("Voulez-vous visualiser le polygone (fenêtre externe) ?") == 1:
            print("\n(Fermer la fenêtre pour continuer)")
            polygon.drawPolygon()
        utils.jumpLines()

        # Menu retry (0 = quitter, 1 = réessayer).
        userRetry = menu.drawBinaryMenu("Calculer l'aire d'un autre polygone ?")
        utils.jumpLines()
