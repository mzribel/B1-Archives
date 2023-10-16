package main

import (
	"os"
	"piscine/piscine-go"

	"github.com/01-edu/z01"
)

func main() {
	var _case int = 96
	if len(os.Args) == 1 {
		return
	}
	args := os.Args[1:]
	if args[0] == "--upper" {
		_case = 64
		args = args[1:]
	}

	for _, arg := range args {
		if piscine.Atoi(arg) >= 1 && piscine.Atoi(arg) <= 26 {
			z01.PrintRune(rune(piscine.Atoi(arg) + _case))
		} else {
			z01.PrintRune(' ')
		}
	}
}
