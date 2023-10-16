package piscine

func CollatzCountdown(start int) int {
	if start < 1 {
		return -1
	}
	stepcount := 0

	for start != 1 {
		if start%2 == 0 {
			start = start / 2
		} else {
			start = 3*start + 1
		}
		stepcount++
	}
	return stepcount
}
