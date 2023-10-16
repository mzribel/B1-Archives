package piscine

func Compact(ptr *[]string) int {
	newarr := []string{}
	for _, v := range *ptr {
		if v != "" {
			newarr = append(newarr, v)
		}
	}
	*ptr = newarr
	return len(newarr)
}
