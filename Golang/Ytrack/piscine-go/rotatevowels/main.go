package main

import (
	"os"

	"github.com/01-edu/z01"
)

var Vowels = []rune{'A', 'a', 'E', 'e', 'I', 'i', 'O', 'o', 'U', 'u'}

func isVowel(char rune) bool {
	for _, vowel := range Vowels {
		if char == vowel {
			return true
		}
	}
	return false
}

func main() {
	args := os.Args[1:]
	if len(args) == 0 {
		z01.PrintRune('\n')
	}

	// Concat all args
	var str string
	for _, arg := range args {
		str += arg + " "
	}
	j := len(str)
	for i := 0; i < len(str); i++ {
		if isVowel(rune(str[i])) {
			j--
			for !isVowel(rune(str[j])) {
				j--
			}
			z01.PrintRune(rune(str[j]))
			continue
		}
		z01.PrintRune(rune(str[i]))
	}

}
