from TP3 import christmastree, menu, calculatearea, utils, justeprix

if __name__ == '__main__':
    tp2_mainMenu = menu.Menu(textBox=menu.Box("M E N U  P R I N C I P A L"), options=[
        menu.MenuOption("Calculer l'aire d'un polygone", calculatearea.main),
        menu.MenuOption("Le Juste Prix", justeprix.main),
        menu.MenuOption("Le Sapin de Noël", christmastree.main),
        menu.MenuOption("... Une quatrième option?", utils.drawMeSomething)
    ]).drawMenu()
