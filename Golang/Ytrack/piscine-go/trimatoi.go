package piscine

func TrimAtoi(s string) int {
	result := 0
	positive_int := true
	for i := 0; i < len(s); i++ {
		if s[i] == 45 && result == 0 {
			positive_int = false
		}
		if IsNumeric(string(s[i])) {
			result = result*10 + int(s[i]-48)
		}
	}
	if !positive_int {
		result = -result
	}
	return result
}
