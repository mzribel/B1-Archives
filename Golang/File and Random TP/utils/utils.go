package utils

import (
	"bufio"
	"fmt"
	"os"
	"strings"
)

// SIMILAR TO Array.Prototype.IndexOf, INCLUDES ERROR HANDLE:
func IndexOf(target string, lines []string) (int, error) {
	for index, word := range lines {
		if strings.TrimSpace(word) == target {
			return index, nil
		}
	}
	return -1, fmt.Errorf("word is not in the file")
}

// TAKEN FROM PROJECT-RED
// FUNCTION TO EFFECTIVELY ASK AN INT TO THE USER
// LOOPS UNTIL THE INPUT IS CORRECT:
func DiscardBuffer(r *bufio.Reader) {
	r.Discard(r.Buffered())
}
func GetValidInt() int {
	var answer int
	stdin := bufio.NewReader(os.Stdin)
	for {
		fmt.Println("Let's get a random number between 0 and... [max: 100] ")
		fmt.Printf("   → ")

		// Not valid type:
		if _, err := fmt.Fscanln(stdin, &answer); err != nil {
			fmt.Print("Not even a number !\n\n")
			DiscardBuffer(stdin)
			continue
		}
		if answer == 0 {
			fmt.Print("Between 0 and 0 ? Really ? [1-100]\n\n")
			DiscardBuffer(stdin)
			continue
		}
		if answer > 100 || answer < 0 {
			fmt.Print("Out of range ! [1-100]\n\n")
			DiscardBuffer(stdin)
			continue
		}
		break
	}
	return answer + 1
}
