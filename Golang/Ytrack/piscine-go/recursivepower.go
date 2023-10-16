package piscine

func RecursivePower(nb int, power int) int {
	if power == 0 {
		return 1
	} else if power%2 == 0 {
		return RecursivePower(nb, power/2) * RecursivePower(nb, power/2)
	}
	return nb * RecursivePower(nb, power/2) * RecursivePower(nb, power/2)
}

func RecursivePower2(nb int, power int) int {
	if power > 0 {
		return nb * RecursivePower2(nb, power-1)
	}
	return 1
}
