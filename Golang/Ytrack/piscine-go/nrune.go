package piscine

func NRune(s string, n int) rune {
	if len(s) >= n {
		return rune(s[n-1])
	}
	return rune(0)
}
