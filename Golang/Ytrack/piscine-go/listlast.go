package piscine

func ListLast(l *List) interface{} {
	if l.Head == nil {
		return nil
	}
	curr := l.Head
	for curr.Next != nil {
		curr = curr.Next
	}
	return curr.Data
}
