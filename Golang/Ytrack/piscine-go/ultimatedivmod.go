package piscine

func UltimateDivMod(a *int, b *int) {
	if *b != 0 {
		temp := *a
		*a = *a / *b
		*b = temp % *b
	}
}
