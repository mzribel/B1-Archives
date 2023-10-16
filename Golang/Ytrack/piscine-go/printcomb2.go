package piscine

import (
	"github.com/01-edu/z01"
)

func To_ASCII(nb int) (int, int) {
	var tens = nb/10 + 48
	var units = nb%10 + 48
	return tens, units
}
func PrintComb2() {
	var is_first bool = true

	for i := 0; i < 100; i++ {
		for j := 0; j < 100; j++ {
			if i < j && i != j {
				i1, i2 := To_ASCII(i)
				j1, j2 := To_ASCII(j)

				if !is_first {
					z01.PrintRune(rune(','))
					z01.PrintRune(rune(' '))
				}
				z01.PrintRune(rune(i1))
				z01.PrintRune(rune(i2))
				z01.PrintRune(rune(' '))
				z01.PrintRune(rune(j1))
				z01.PrintRune(rune(j2))
				is_first = false
			}
		}
	}

}
