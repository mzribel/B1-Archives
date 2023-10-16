package piscine

func ListSize(l *List) int {
	var list_size int
	if l.Head != nil {
		list_size = 1
		curr := l.Head
		for curr.Next != nil {
			list_size++
			curr = curr.Next
		}
	}
	return list_size
}
