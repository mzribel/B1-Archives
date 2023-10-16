package piscine

func BasicAtoi(s string) int {
	result := 0
	if !(IsNumeric(s)) {
		return result
	}
	for i := 0; i < len(s); i++ {
		result = result*10 + int(s[i]-48)
	}
	return result
}
