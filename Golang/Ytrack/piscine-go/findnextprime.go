package piscine

import "fmt"

func FindNextPrime(nb int) int {
	if nb <= 1 {
		return 2
	}
	for isNextPrime := !IsPrime(nb); isNextPrime; isNextPrime = !IsPrime(nb) {
		fmt.Println(nb)
		nb++
	}
	return nb
}
