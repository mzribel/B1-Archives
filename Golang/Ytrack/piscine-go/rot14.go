package piscine

func LetterRot(letter rune, min int) rune {
	return rune(min + (int(letter)-min+14)%26)
}

func rot14(str string) string {
	var newstring string
	for _, char := range str {
		temp := char
		if temp >= 65 && temp <= 90 {
			temp = LetterRot(temp, 65)
		} else if temp >= 97 && temp <= 122 {
			temp = LetterRot(temp, 97)
		}
		newstring += string(temp)
	}
	return newstring
}
