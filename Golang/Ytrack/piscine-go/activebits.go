package piscine

func NbrtoBin(n int) []int {
	var arr []int

	if n < 0 {
		n = -n
	}

	for n > 2 {
		arr = append(arr, n%2)
		n /= 2
	}
	arr = append(arr, n)
	return arr
}

func ActiveBits(n int) int {
	count := 0
	for _, nbr := range NbrtoBin(n) {
		count += nbr
	}
	return count
}
