package piscine

func InterativeFactorial(index int) int {
	result := 0
	for i := 0; i < index+1; i++ {
		result = result * i
	}
	return result
}
