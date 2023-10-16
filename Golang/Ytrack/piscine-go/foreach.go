package piscine

func ForEach(f func(int) int, a []int) {
	for _, element := range a {
		f(element)
	}
}
