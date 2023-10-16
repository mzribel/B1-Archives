package piscine

func IterativePower(nb int, power int) int {
	result := 1
	for i := 1; i < power+1; i++ {
		result = result * nb
	}
	return result
}
