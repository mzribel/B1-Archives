package piscine

func Abort(a, b, c, d, e int) int {
	arr := []int{a, b, c, d, e}
	SortIntegerTable(arr)
	return arr[len(arr)/2]
}
