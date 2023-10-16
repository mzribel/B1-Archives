package main

import (
	"os"
	"piscine/piscine-go"

	"github.com/01-edu/z01"
)

func main() {
	args := os.Args[1:]
	piscine.SortWordArr(args)
	for i, arg := range args {
		piscine.PrintStr(arg)
		if i != len(args)-1 {
			z01.PrintRune('\n')
		}
	}
}
