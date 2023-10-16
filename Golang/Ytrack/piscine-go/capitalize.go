package piscine

func Capitalize(s string) string {
	new_string := ""
	for i := 0; i < len(s); i++ {
		temp := s[i]
		// If in first position or char before is neither alphanum or whitespace && char not in uppercase:
		if (i == 0 || !IsAlpha(string(s[i-1])) || s[i-1] == 32) && (temp >= 97 && temp <= 122) {
			temp = s[i] - 32
			// If char before is alphanum and not a whitespace && char is not in lowercase:
		} else if (!IsAlpha(string(s[i-1])) && s[i-1] != 32) && s[i] >= 65 && s[i] <= 90 {
			temp = s[i] + 32
		}
		new_string += string(temp)
	}
	return new_string
}
