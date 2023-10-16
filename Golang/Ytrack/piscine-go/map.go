package piscine

func Map(f func(int) bool, a []int) []bool {
	var new_arr []bool
	for _, element := range a {
		new_arr = append(new_arr, f(element))
	}
	return new_arr
}
