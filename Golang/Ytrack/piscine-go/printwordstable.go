package piscine

import "github.com/01-edu/z01"

func PrintWordsTables(a []string) {
	if len(a) != 0 {
		for _, word := range a {
			for _, letter := range word {
				z01.PrintRune(rune(letter))
			}
			z01.PrintRune('\n')
		}
	}
}
