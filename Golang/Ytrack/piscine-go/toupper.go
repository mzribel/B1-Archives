package piscine

func ToUpper(s string) string {
	var new_string string
	for i := 0; i < len(s); i++ {
		if s[i] >= 97 && s[i] <= 122 {
			new_string += string(s[i] - 32)
		} else {
			new_string += string(s[i])
		}
	}
	return new_string
}
