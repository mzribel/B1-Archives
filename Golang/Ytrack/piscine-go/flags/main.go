package main

import (
	"fmt"
	"os"
	"piscine/piscine-go"
)

func PrintHelp() {
	fmt.Println()
	fmt.Print("--insert\n")
	fmt.Print("  -i	 This flag inserts the string into the string passed as argument.\n")
	fmt.Print("--order\n")
	fmt.Print("  -o	 This flag will behave like a boolean, if it is called it will order the argument.\n")
}

func ProcessInput(basestr, insertstr string, ordered bool) {
	basestr += insertstr
	if ordered {
		var newstring []string
		for _, letter := range basestr {
			newstring = append(newstring, string(letter))
		}
		piscine.SortWordArr(newstring)
		for _, letter := range newstring {
			fmt.Print(string(letter))
		}
		fmt.Println()
		return
	}
	fmt.Println(basestr)
}
func ContainsFlag(arg string) string {
	if piscine.Index(arg, "-i=") == 0 || piscine.Index(arg, "--insert=") == 0 {
		return "i"
	} else if piscine.Index(arg, "-o") == 0 || piscine.Index(arg, "--order") == 0 {
		return "o"
	} else if piscine.Index(arg, "-h") == 0 || piscine.Index(arg, "--help") == 0 {
		return "h"
	}
	return "u"
}
func CheckArgs(args []string) (bool, string, string, bool) {
	var validArgs, ordered bool
	var insertStr, baseStr string

	flagsSet := map[string]bool{"insertStr": false, "baseStr": false, "ordered": false}

	for _, arg := range args {
		currFlag := ContainsFlag(arg)
		if currFlag == "i" && !flagsSet["insertStr"] {
			flagsSet["insertStr"] = true
			insertStr = arg[piscine.Index(arg, "=")+1:]
			continue
		}
		if currFlag == "o" && !flagsSet["ordered"] {
			flagsSet["ordered"] = true
			ordered = true
			continue
		}
		if currFlag == "u" && !flagsSet["baseStr"] {
			flagsSet["baseStr"] = true
			baseStr = arg
			continue
		}
		return validArgs, insertStr, baseStr, ordered
	}
	validArgs = true
	return validArgs, insertStr, baseStr, ordered
}

func main() {
	args := os.Args[1:]
	validArgs, insertStr, baseStr, ordered := CheckArgs(args)
	if len(args) == 0 || !validArgs {
		PrintHelp()
	} else {
		ProcessInput(baseStr, insertStr, ordered)
	}
}
