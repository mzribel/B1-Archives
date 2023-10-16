package piscine

func SortIntegerTable(table []int) {
	if len(table) > 1 {
		for i := 0; i < len(table)-1; i++ {
			for j := 0; j < len(table)-1; j++ {
				if CompareInt(table[j], table[j+1]) == 1 {
					table[j], table[j+1] = table[j+1], table[j]
				}
			}
		}
	}
}
