package piscine

import (
	"github.com/01-edu/z01"
)

func NbrToArr(n int) []int {
	var arr []int
	for ; n > 10; n /= 10 {
		arr = append(arr, n%10)
	}
	arr = append(arr, n)
	return arr
}
func ArrSlice(slice []int, s int) []int {
	var new_slice []int = append(slice[:s], slice[s+1:]...)
	return new_slice
}
func PrintNbrInOrder(n int) {
	if n < 0 {
		return
	}
	arr := NbrToArr(n)
	min_val := 10
	min_index := 0
	for len(arr) > 0 {
		for i := 0; i < len(arr); i++ {
			if arr[i] < min_val {
				min_index = i
				min_val = arr[i]
			}
		}
		z01.PrintRune(rune(min_val + 48))
		arr = ArrSlice(arr, min_index)
		min_index = 0
		min_val = 10
	}
}
