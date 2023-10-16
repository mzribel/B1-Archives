package piscine

func GetMinLen(len1, len2 int) int {
	if len1 > len2 {
		return len2
	}
	return len1
}

func Compare(str1, str2 string) int {
	min_len := GetMinLen(len(str1), len(str2))
	for i := 0; i < min_len; i++ {
		if str1[i] > str2[i] {
			return 1
		} else if str1[i] < str2[i] {
			return -1
		}
	}
	if len(str1) > len(str2) {
		return 1
	} else if len(str1) < len(str2) {
		return -1
	}
	return 0
}
