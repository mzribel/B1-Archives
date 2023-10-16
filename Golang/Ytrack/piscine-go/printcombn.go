package piscine

import "github.com/01-edu/z01"

func calc_power(x, y int) int {
	if y == 0 {
		return 1
	} else if y%2 == 0 {
		return calc_power(x, y/2) * calc_power(x, y/2)
	} else {
		return x * calc_power(x, y/2) * calc_power(x, y/2)
	}
}

func convert_to_arr(base_nb int, n int) []int {
	rest := base_nb
	position := n
	arr := make([]int, n)

	for i := n - 1; i >= 1; i-- {
		arr[len(arr)-position] = rest / calc_power(10, i)
		rest %= calc_power(10, i)
		position--
	}
	arr[len(arr)-1] = rest
	return arr
}
func check_is_valid(arr []int) bool {
	valid := true
	for i := 0; i < len(arr)-1; i++ {
		if arr[i] >= arr[i+1] {
			valid = false
		}
	}

	return valid
}
func PrintCombN(n int) int {
	var arr []int
	var is_first bool = true
	for i := 0; i < calc_power(10, n); i++ {
		arr = convert_to_arr(i, n)
		if check_is_valid(arr) {
			if !is_first {
				z01.PrintRune(rune(','))
				z01.PrintRune(rune(' '))
			}
			for j := 0; j < len(arr); j++ {
				z01.PrintRune(rune(arr[j] + 48))
			}
			is_first = false
		}
	}
	return 0
}
