package piscine

func FirstRune(s string) rune {
	if len(s) > 0 {
		return rune(s[0])
	}
	return rune(0)
}
