package piscine

func recursiveFactorial(index int) int {
	if index == 1 {
		return 1
	}
	if index > 1 {
		return index * recursiveFactorial(index-1)
	}
	return 0
}
