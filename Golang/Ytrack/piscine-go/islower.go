package piscine

func IsLower(s string) bool {
	if len(s) == 0 {
		return false
	}
	for i := 0; i < len(s); i++ {
		if s[i] < 97 || s[i] > 122 {
			return false
		}
	}
	return true
}
