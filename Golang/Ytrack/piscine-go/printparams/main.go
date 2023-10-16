package main

import (
	"os"

	"github.com/01-edu/z01"
)

func main() {
	args := os.Args
	for i, arg := range args {
		for _, char := range arg {
			z01.PrintRune(char)
		}
		if i != len(args)-1 {
			z01.PrintRune('\n')
		}
	}
}
