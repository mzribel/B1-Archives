package piscine

func StrRev(s string) string {
	new_string := ""
	for i := len(s) - 1; i >= 0; i-- {
		new_string += string(s[i])
	}
	return new_string
}
