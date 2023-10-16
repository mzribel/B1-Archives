package piscine

func SortWordArr(a []string) {
	if len(a) > 1 {
		for i := 0; i < len(a)-1; i++ {
			for j := 0; j < len(a)-1; j++ {
				if Compare(a[j], a[j+1]) == 1 {
					a[j], a[j+1] = a[j+1], a[j]
				}
			}
		}
	}
}
