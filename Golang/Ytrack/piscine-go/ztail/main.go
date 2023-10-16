package main

import (
	"fmt"
	"io/ioutil"
	"os"
	"piscine/piscine-go"
)

func main() {
	args := os.Args[1:]
	if len(args) < 3 {
		println("Invalid number of arguments.")
		os.Exit(1)
	}
	var size int = piscine.TrimAtoi(args[1])
	if args[0] != "-c" || size < 0 {
		println("Invalid integer.")
		os.Exit(1)
	}
	error_status := 0
	for _, arg := range args[2:] {
		content, error := ioutil.ReadFile(arg)
		if error != nil {
			fmt.Printf("ERROR: open %v: no such file or directory.\n\n", arg)
			error_status = 1
			continue
		}
		fmt.Printf("==> %v <==\n", arg)
		if len(content) < size {
			fmt.Printf(string(content) + "\n\n")
			continue
		}
		fmt.Printf(string(content[len(content)-size:]) + "\n\n")
	}
	os.Exit(error_status)
}
