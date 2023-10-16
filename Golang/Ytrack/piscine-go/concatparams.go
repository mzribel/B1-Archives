package piscine

func ConcatParams(args []string) string {
	var new_string string
	if len(args) > 0 {
		for i, arg := range args {
			new_string += arg
			if i != len(args)-1 {
				new_string += "\n"
			}
		}
	}
	return new_string
}
