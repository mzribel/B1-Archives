package piscine

func Atoi(s string) int {
	result := 0
	positive_int := true

	if len(s) == 0 {
		return result
	}
	if s[0] == 45 || s[0] == 43 {
		if s[0] == 45 {
			positive_int = false
		}
		s = s[1:]
	}
	if !(IsNumeric(s)) {
		return result
	}
	for i := 0; i < len(s); i++ {
		result = result*10 + int(s[i]-48)
	}
	if !positive_int {
		result = -result
	}
	return result
}
