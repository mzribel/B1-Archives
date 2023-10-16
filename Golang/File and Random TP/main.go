package main

import (
	"fmt"
	"log"
	"math/rand"
	"os"
	"strconv"
	"strings"
	"time"

	"fileRandom/utils"
)

func main() {
	// Get content from the file and checks for errors:
	var instructions string
	var err error
	content, err := os.ReadFile("File.txt")
	if err != nil {
		log.Fatal(err)
	}

	// Splits the file into a splice of lines (== words) (bytes 13 and 10 between each word,
	// therefore separator is esc. chars "carriage return" + "newline feed")
	fileLines := strings.Split(string(content), "\r\n")

	if instructions, err = Solve(fileLines); err != nil {
		log.Fatal(err)

	}

	fmt.Println("\nSecret Mission: ")
	fmt.Printf("     >>> %v <<<\n\n", strings.ToUpper(instructions))
	answer := utils.GetValidInt()
	fmt.Printf("   Result = %v\n", GetRandomInt(answer))

}

func GetRandomInt(max int) int {
	rand.Seed(time.Now().UTC().UnixNano())
	return rand.Intn(max)
}

// SOLVES ALL FOUR RIDDLES AT THE SAME TIME:
func Solve(lines []string) (result string, err error) {
	var content string
	var line int

	// Empty file check:
	if len(lines) == 0 {
		return "", err
	}

	// FIRST CLUE:
	// First word of the file:
	result = lines[0]

	// SECOND CLUE:
	// Word at the opposite of the previous = last word of the file:
	result += " " + lines[len(lines)-1]

	// THIRD CLUE:
	/* 1. Gets the index (line-1) of "before"; */
	if line, err = utils.IndexOf("before", lines); err != nil {
		return "", err
	}
	/* 2. Retrieves the content of line after "before" (= "23")
	   and converts it into a readable int: */
	if line, err = strconv.Atoi(lines[line+1]); err != nil {
		return "", err
	}
	/* 3. Retrieve word at line 23: */
	result += " " + lines[line-1]

	// FOURTH CLUE:
	/* 1. Gets the index of "now";
	   2. Retrieves the preceding line's content:*/
	if line, err = utils.IndexOf("now", lines); err != nil {
		return "", err
	}
	content = lines[line-1]
	/* 3. Divide ASCII code of second char
	   by total number of lines in the file (= number of words);
	   4. Retrieves the word at that line: */
	line = int(content[1]) / len(lines)
	result += " " + lines[line-1]

	return result, nil
}
