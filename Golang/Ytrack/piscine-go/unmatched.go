package piscine

func Unmatch(a []int) int {
	if len(a) == 0 {
		return -1
	}
	for _, number := range a {
		count := 0
		for i := 0; i < len(a); i++ {
			if number == a[i] {
				count++
			}
		}
		if count%2 == 1 {
			return number
		}
	}
	return -1
}
