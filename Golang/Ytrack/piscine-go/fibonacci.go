package piscine

func Fibonacci(nb int) int {
	if nb == 0 || nb == 1 {
		return nb
	} else if nb > 0 {
		return Fibonacci(nb-1) + Fibonacci(nb-2)
	}
	return -1
}
