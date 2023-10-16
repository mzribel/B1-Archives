package piscine

func BasicJoin(elems []string) string {
	var new_string string
	for i := 0; i < len(elems); i++ {
		new_string += elems[i]
	}
	return new_string
}
