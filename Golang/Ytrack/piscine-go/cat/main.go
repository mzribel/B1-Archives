package main

import (
	"fmt"
	"io/ioutil"
	"os"
)

func main() {
	if len(os.Args) == 1 {
		return
	}
	args := os.Args[1:]
	for _, arg := range args {
		content, error := ioutil.ReadFile(arg)
		if error != nil {
			fmt.Printf("ERROR: open %v: no such file or directory.\nexit status 1\n", arg)
			return
		}
		fmt.Println(string(content))
	}
}
