package piscine

func Max(a []int) int {
	if len(a) == 0 {
		return 0
	}
	bigger := a[0]
	for _, number := range a {
		if number > bigger {
			bigger = number
		}
	}
	return bigger
}
