package piscine

import "github.com/01-edu/z01"

func PrintNbr(nb int) int {
	rest := nb
	var arr []int
	if nb < 0 {
		z01.PrintRune('-')
		rest = 0 - (nb)
	}

	for rest > 10 {
		arr = append(arr, rest%10+48)
		rest /= 10
	}
	arr = append(arr, rest+48)
	for n := len(arr); n > 0; n-- {
		z01.PrintRune(rune(arr[n-1]))
	}

	return 0
}
