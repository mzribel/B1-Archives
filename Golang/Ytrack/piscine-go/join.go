package piscine

func Join(strs []string, sep string) string {
	var new_string string
	for i := 0; i < len(strs); i++ {
		new_string += strs[i]
		if i <= len(strs)-2 {
			new_string += sep
		}
	}
	return new_string
}
