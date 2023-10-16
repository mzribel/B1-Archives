package piscine

func MakeRange(min, max int) []int {
	var arr []int
	if min >= max {
		return arr
	}
	arr = make([]int, max-min)
	for i := range arr {
		arr[i] = min + i
	}
	return arr
}
