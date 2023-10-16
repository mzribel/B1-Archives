package piscine

func CountIf(f func(string) bool, tab []string) int {
	result := 0

	for _, element := range tab {
		if f(element) {
			result++
		}
	}
	return result
}
