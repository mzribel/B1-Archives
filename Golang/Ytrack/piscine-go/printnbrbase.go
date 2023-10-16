package piscine

import (
	"github.com/01-edu/z01"
)

func BaseIsValid(base string) bool {
	if len(base) < 2 {
		return false
	}
	for i := 0; i < len(base); i++ {
		if base[i] == 43 || base[i] == 46 {
			return false
		}
		for j := i + 1; j < len(base); j++ {
			if base[i] == base[j] {
				return false
			}
		}
	}
	return true
}

func PrintNbrBase(n int, base string) {
	if !BaseIsValid(base) {
		z01.PrintRune('N')
		z01.PrintRune('V')
		return
	}
	base_int := len(base)
	var arr []rune

	if n < 0 {
		n = -n
		z01.PrintRune('-')
	}

	for n > base_int {
		arr = append(arr, rune(base[n%base_int]))
		n /= base_int
	}
	arr = append(arr, rune(base[n]))
	for n := len(arr); n > 0; n-- {
		z01.PrintRune(arr[n-1])
	}
}
