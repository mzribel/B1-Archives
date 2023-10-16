package piscine

func Index(s string, toFind string) int {
	if len(s) == 0 || len(toFind) == 0 || len(s) < len(toFind) {
		return -1
	}
	for i := 0; i <= len(s)-len(toFind); i++ {
		if s[i] == toFind[0] {
			if Compare(s[i:i+len(toFind)], toFind) == 0 {
				return i
			}
		}
	}
	return -1
}
