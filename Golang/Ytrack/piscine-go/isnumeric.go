package piscine

func IsNumeric(s string) bool {
	if len(s) == 0 {
		return false
	}
	for i := 0; i < len(s); i++ {
		if s[i] < 48 || s[i] > 57 {
			return false
		}
	}
	return true
}
