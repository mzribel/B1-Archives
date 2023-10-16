package piscine

func SplitWhiteSpaces(s string) []string {
	var new_string []string

	if len(s) != 0 {
		var temp string
		for i := 0; i < len(s); i++ {
			if (s[i] == 10 || s[i] == 9 || s[i] == 32) && temp != "" {
				new_string = append(new_string, temp)
				temp = ""
				continue
			}
			temp += string(s[i])
		}
		new_string = append(new_string, temp)
	}
	return new_string
}
